package com.projeto.web.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Login;
import com.projeto.models.model.Usuario;
import com.projeto.models.model.dto.UsuarioLogadoDTO;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping(value="/rest")
public class LoginRestController {
	
   private String token;

   @Autowired	
   private UsuarioService usuarioService;
   
   @Autowired
   private JwtTokenProvider tokenProvider;
		
   @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE,
		   produces = MediaType.APPLICATION_JSON_VALUE)	
   public UsuarioLogadoDTO login(@RequestBody @Valid Login login) {

		Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(login.getEmail());

		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não está cadastrado! ");
		}

		if ( login.getEmail().equals(usuario.get().getEmail()) && usuario.get().isAtivo() == Boolean.FALSE) {
			throw new LockedException("Usuário está bloqueado no sistema !");
		}
		
		if ( login.getEmail().equals(usuario.get().getEmail()) && BCrypt.checkpw(login.getPassword(), usuario.get().getPassword())){
			new UsernamePasswordAuthenticationToken(usuario.get(), usuario.get().getPassword(), usuario.get().getAuthorities());
		} else {
			throw new BadCredentialsException("A senha informada é inválida ou está incorreta!"); 
		}
		
		setToken(tokenProvider.createToken(usuario.get().getEmail(), usuario.get().getRoles()));
		
		UsuarioLogadoDTO usuarioLogadoDTO = new UsuarioLogadoDTO();
		
		usuarioLogadoDTO.setEmail(usuario.get().getEmail());
		usuarioLogadoDTO.setUsername(usuario.get().getUsername());
		usuarioLogadoDTO.setToken(this.getToken());
		
		return usuarioLogadoDTO;
   }

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
   
   
  
   
   
   
   
   
   
   
	
}
