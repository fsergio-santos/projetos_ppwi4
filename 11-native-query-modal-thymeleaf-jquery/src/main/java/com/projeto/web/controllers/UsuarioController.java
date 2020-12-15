package com.projeto.web.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.model.Usuario;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.repository.pagination.Pagina;
import com.projeto.models.service.DepartamentoService;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;
import com.projeto.web.response.ResponseSelect2Data;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@Autowired 
	private DepartamentoService departamentoService;
	

	
	@GetMapping(value = "/listar")
	public ModelAndView listarUsuario(UsuarioFiltro usuarioFiltro, 
			HttpServletRequest request,
			@RequestParam(value="size", required = false) Optional<Integer> size,
			@RequestParam(value="page", required = false) Optional<Integer> page,
			@RequestParam(value="sort", required = false) Optional<String> sort,
			@RequestParam(value="dir", required = false) Optional<String> dir ){
		
		
		Pageable pageable = PageRequest.of(page.orElse(ConfigProjeto.INITIAL_PAGE), 
				  						   size.orElse(ConfigProjeto.SIZE),
				  						   getDirection(dir), getAtributte(sort));
		
		Page<Usuario> listaUsuario = usuarioService.listUsuarioByPage(usuarioFiltro, pageable);
		
		Pagina<Usuario> pagina = new Pagina<>(listaUsuario, 
											  size.orElse(ConfigProjeto.SIZE), 
											  request);
		
		ModelAndView mv = new ModelAndView("/usuario/listar");
		mv.addObject("usuarioFiltro", usuarioFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", size.orElse(ConfigProjeto.SIZE));
		mv.addObject("dir", dir.orElse("asc"));
		mv.addObject("sort", sort.orElse("id"));
		mv.addObject("pagina", pagina);
		return mv;
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario) {
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/incluir")
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			 usuarioService.save(usuario);
		} catch (EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} catch (ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		}
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/alterar/{id}")
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findUserById(id);
		model.addAttribute("departamentos", usuario.getDepartamento());
		System.out.println(usuario.getRoles());
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/alterar")
	public String salvarEdicaoUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "/usuario/alterar";
		}
		try {
			usuario = usuarioService.update(usuario);
		} catch (EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/alterar";
		} catch (ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/alterar";
		}
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String buscarUsuarioParaExclusao(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findUserById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/excluir";
	}

	@PostMapping(value = "/excluir")
	public String excluirEdicaoUsuario(Usuario usuario) {
		usuarioService.deleteById(usuario.getId());
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/consultar/{id}")
	public String buscarUsuarioParaConsulta(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/consultar";
	}
	
	
	@ResponseBody
	@GetMapping(value="/buscaDepartamento")
	public List<ResponseSelect2Data> selectDepartamento(@RequestParam(value="q", required = false) String query){
		return StringUtils.isEmpty(query)
				       ? departamentoService.buscaSemParamentro() 
				       : departamentoService.buscaPorParametroDepartamento(query);
	}
	
    
	
	private String getAtributte(Optional<String> sort) {
		return sort.orElse("id");
	}


	private Direction getDirection(Optional<String> dir) {
		String direcao = dir.orElse("asc");
		return direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
	
	

}
