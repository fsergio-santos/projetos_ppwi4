package com.projeto.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Login;
import com.projeto.models.model.Usuario;
import com.projeto.models.service.UsuarioService;

@RestController
@RequestMapping(value="/rest")
public class LoginRestController {

   @Autowired	
   private UsuarioService usuarioService;
		
   @PostMapping(value="/login")	
   public Usuario login(@RequestBody Login login) {
	    System.out.println("passando polo login ");
		Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(login.getEmail());
     	return usuario.get();
   }
	
}
