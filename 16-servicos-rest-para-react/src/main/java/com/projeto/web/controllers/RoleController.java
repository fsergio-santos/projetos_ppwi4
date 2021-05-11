package com.projeto.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.models.model.Role;
import com.projeto.models.service.RoleService;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
		
	@GetMapping(value="/listar")
	public ModelAndView listarRole() {
		ModelAndView mv = new ModelAndView("/role/listar");
		mv.addObject("roles", roleService.findAll());
		return mv;
	}    
	
	//@GetMapping(value="/role/cadastro")
	@RequestMapping(value="/cadastro", method = RequestMethod.GET)
	public String cadastroRole(Role role) {
		//model.addAttribute("role", role);
		return "/role/cadastro";
	}
	
	@PostMapping(value="/incluir")
	//@RequestMapping(value="/incluir", method = RequestMethod.POST)
	public String inserirRole(@Valid Role role, BindingResult result) {
		if ( result.hasErrors()) {
			return "/role/cadastro";
		}
		roleService.save(role);
		return "redirect:/role/listar";
	}
	
	@GetMapping(value="/alterar/{id}")
	public String buscarRoleParaAlteracao(@PathVariable Long id,  Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		return "/role/cadastro";
	}
	
	@PostMapping(value="/alterar")
	public String salvarEdicaoRole(@Valid Role role, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
			model.addAttribute("role", role);
			return "/role/cadastro";
		}
		role = roleService.update(role);
		return "redirect:/role/listar";
	}
		
	@GetMapping(value="/excluir/{id}")
	public String buscarRoleParaExclusao(@PathVariable Long id, Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		return "/role/excluir";
	}
	
	
	@PostMapping(value="/excluir")
	public String excluirEdicaoRole(Role role) {
		roleService.deleteById(role.getId());
		return "redirect:/role/listar";
	}
	
	@GetMapping(value="/consultar/{id}")
	public String buscarRoleParaConsulta(@PathVariable Long id, Model model) {
		Role role = roleService.findById(id);
		model.addAttribute("role", role);
		return "/role/consultar";
	}
	

}
