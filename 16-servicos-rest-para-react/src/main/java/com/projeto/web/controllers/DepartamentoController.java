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

import com.projeto.models.model.Departamento;
import com.projeto.models.service.DepartamentoService;

@Controller
@RequestMapping(value="/departamento")
public class DepartamentoController {

	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping(value="/listar")
	public ModelAndView listarDepartamento() {
		ModelAndView mv = new ModelAndView("/departamento/listar");
		mv.addObject("departamentos", departamentoService.findAll());
		return mv;
	}    
	
	//@GetMapping(value="/departamento/cadastro")
	@RequestMapping(value="/cadastro", method = RequestMethod.GET)
	public String cadastroDepartamento(Departamento departamento) {
		//model.addAttribute("departamento", departamento);
		return "/departamento/cadastro";
	}
	
	@PostMapping(value="/incluir")
	//@RequestMapping(value="/incluir", method = RequestMethod.POST)
	public String inserirDepartamento(@Valid Departamento departamento, BindingResult result) {
		if ( result.hasErrors()) {
			return "/departamento/cadastro";
		}
		departamentoService.save(departamento);
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/alterar/{id}")
	public String buscarDepartamentoParaAlteracao(@PathVariable Long id,  Model model) {
		
		Departamento departamento = departamentoService.findById(id);
		model.addAttribute("departamento", departamento);
		return "/departamento/cadastro";
	}
	
	@PostMapping(value="/alterar")
	public String salvarEdicaoDepartamento(@Valid Departamento departamento, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
			model.addAttribute("departamento", departamento);
			return "/departamento/cadastro";
		}
		departamento = departamentoService.update(departamento);
		return "redirect:/departamento/listar";
	}
		
	@GetMapping(value="/excluir/{id}")
	public String buscarDepartamentoParaExclusao(@PathVariable Long id, Model model) {
		Departamento departamento = departamentoService.findById(id);
		model.addAttribute("departamento", departamento);
		return "/departamento/excluir";
	}
	
	
	@PostMapping(value="/excluir")
	public String excluirEdicaoDepartamento(Departamento departamento) {
		departamentoService.deleteById(departamento.getId());
		return "redirect:/departamento/listar";
	}
	
	@GetMapping(value="/consultar/{id}")
	public String buscarDepartamentoParaConsulta(@PathVariable Long id, Model model) {
		Departamento departamento = departamentoService.findById(id);
		model.addAttribute("departamento", departamento);
		return "/departamento/consultar";
	}
}
