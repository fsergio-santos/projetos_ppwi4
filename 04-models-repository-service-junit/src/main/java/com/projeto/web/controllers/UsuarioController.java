package com.projeto.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.models.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@RequestMapping(value="/usuario")
	public String mostrarPaginadeUsuario() {
		return "usuario/list";
	}

	public String salvarUsuario() {
		return null;
	}
	
}
