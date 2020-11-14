package com.projeto.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto.models.model.Usuario;
import com.projeto.models.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	//@GetMapping(value="/usuario/cadastro")
	@RequestMapping(value="/usuario/cadastro", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario, Model model) {
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}
	
	
	@PostMapping(value="/usuario/incluir")
	//@RequestMapping(value="/usuario/incluir", method = RequestMethod.POST)
	public String inserirUsuario(Usuario usuario) {
		
		System.out.println(usuario.toString());
		
		return "/usuario/cadastro";
	}
	
	
}
