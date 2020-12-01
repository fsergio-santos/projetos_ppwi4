package com.projeto.web.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.model.Departamento;
import com.projeto.models.model.Usuario;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.service.DepartamentoService;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;

@Controller
@RequestMapping(value="/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DepartamentoService departamentoService;
		
	@GetMapping(value="/listar")
	public ModelAndView listarUsuario(UsuarioFiltro usuarioFiltro) {
		ModelAndView mv = new ModelAndView("/usuario/listar");
		mv.addObject("usuarioFiltro", usuarioFiltro);
		mv.addObject("pageSizes", ConfigProjeto.PAGE_SIZES);
		mv.addObject("size", ConfigProjeto.SIZE);
		mv.addObject("usuarios", usuarioService.findAll());
		return mv;
	}    
	
	//@GetMapping(value="/usuario/cadastro")
	@RequestMapping(value="/cadastro", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario) {
		//model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}
	
	@PostMapping(value="/incluir")
	//@RequestMapping(value="/incluir", method = RequestMethod.POST)
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result) {
		if ( result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			usuarioService.save(usuario);
		} catch(EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} catch(ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		}
		return "redirect:/usuario/listar";
	}
	
	@GetMapping(value="/alterar/{id}")
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}
	
	@PostMapping(value="/alterar")
	public String salvarEdicaoUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
			model.addAttribute("usuario", usuario);
			return "/usuario/cadastro";
		}
		usuario = usuarioService.update(usuario);
		return "redirect:/usuario/listar";
	}
		
	@GetMapping(value="/excluir/{id}")
	public String buscarUsuarioParaExclusao(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/excluir";
	}
	
	
	@PostMapping(value="/excluir")
	public String excluirEdicaoUsuario(Usuario usuario) {
		usuarioService.deleteById(usuario.getId());
		return "redirect:/usuario/listar";
	}
	
	@GetMapping(value="/consultar/{id}")
	public String buscarUsuarioParaConsulta(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/consultar";
	}
	
	@ModelAttribute("departamentos")
    public List<Departamento> listarTodosDepartamento(){
    	return departamentoService.findAll();
    }
	
	
}
