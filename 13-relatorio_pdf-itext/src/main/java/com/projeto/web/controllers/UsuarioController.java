package com.projeto.web.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.model.Usuario;
import com.projeto.models.reports.UsuarioReportPdf;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.repository.pagination.PageRequestConfig;
import com.projeto.models.repository.pagination.Pagina;
import com.projeto.models.service.DepartamentoService;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;
import com.projeto.models.service.exception.NegocioException;
import com.projeto.web.response.ResponseSelect2Data;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@Autowired 
	private DepartamentoService departamentoService;
	
	@Autowired
	private UsuarioReportPdf usuarioReportPdf;
	
	@GetMapping(value = "/listar")
	public String listarUsuario(UsuarioFiltro usuarioFiltro, 
			HttpServletRequest request, Model model, 
			@RequestParam(value="size", required = false) Optional<Integer> size,
			@RequestParam(value="page", required = false) Optional<Integer> page,
			@RequestParam(value="sort", required = false) Optional<String> sort,
			@RequestParam(value="dir", required = false) Optional<String> dir ){
		
		Pageable pageable = PageRequestConfig.requestPage(size, page, sort, dir);
		
		Page<Usuario> listaUsuario = usuarioService.listUsuarioByPage(usuarioFiltro, pageable);
		
		Pagina<Usuario> pagina = new Pagina<>(listaUsuario, 
											  size.orElse(ConfigProjeto.SIZE), 
											  request);
		
		
		model.addAttribute("usuarioFiltro", usuarioFiltro);
		model.addAttribute("pageSizes", ConfigProjeto.PAGE_SIZES);
		model.addAttribute("size", size.orElse(ConfigProjeto.SIZE));
		model.addAttribute("dir", dir.orElse("asc"));
		model.addAttribute("sort", sort.orElse("id"));
		model.addAttribute("pagina", pagina);
		
		return "/usuario/listar";
	}

	

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario, Model model) {
		model.addAttribute("titulo", "Inclusão de Usuário");
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/incluir")
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash) {

		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			 usuarioService.save(usuario);
			 flash.addFlashAttribute("success","Usuário cadastrado com sucesso!");
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
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario = usuarioService.findUserById(id);
 		model.addAttribute("departamentos", usuario.getDepartamento());
		System.out.println(usuario.getRoles());
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/alterar")
	public String salvarEdicaoUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash ) {
		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "/usuario/cadastro";
		}
		try {
			usuario = usuarioService.update(usuario);
			flash.addFlashAttribute("success","Usuário alterado com sucesso!");
		} catch (EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} catch (ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} 
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String buscarUsuarioParaExclusao(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario =  usuarioService.findUserById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/excluir";
	}

	@PostMapping(value = "/excluir")
	public String excluirEdicaoUsuario(Usuario usuario, RedirectAttributes flash) {
	   	usuarioService.deleteById(usuario.getId());
		flash.addFlashAttribute("success", "Usuário excluído com sucesso");
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/consultar/{id}")
	public String buscarUsuarioParaConsulta(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario =  usuarioService.findUserById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/consultar";
	}
	
	
	@RequestMapping(value="/usuario/relatorio", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimirRelatorioPdf(HttpServletRequest request){
		
		List<Usuario> listaUsuario = usuarioService.findAll();
		ByteArrayInputStream pdf = usuarioReportPdf.generateReport(request, listaUsuario); 
		
		return ResponseEntity.ok()
				             .header("Content-Disposition", "inline; filename=report.pdf")
				             .contentType(MediaType.APPLICATION_PDF)
				             .body(new InputStreamResource(pdf));
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/buscaDepartamento")
	public List<ResponseSelect2Data> selectDepartamento(@RequestParam(value="q", required = false) String query){
		return StringUtils.isEmpty(query)
				       ? departamentoService.buscaSemParamentro() 
				       : departamentoService.buscaPorParametroDepartamento(query);
	}
	

	
	@ExceptionHandler(NegocioException.class)
	public String handlerException( NegocioException ex, RedirectAttributes flash ){
		 flash.addFlashAttribute("error", ex.getMessage());
		 return "redirect:/usuario/listar";
	}
	
	
	
	
}
