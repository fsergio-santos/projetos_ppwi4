package com.projeto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BooleanSupplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.projeto.models.model.Usuario;
import com.projeto.models.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroUsuarioIT {
	
	// happy test -> caminho esperado, tudo certo.
	// unhappy test -> caminho erro, tem algum erro na execução
	
	//cenário -- Usuário
	
	//validação
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testarCadastroDeUsuario() {
		
		Usuario usuario = new Usuario();
		
		usuario.setUsername("Francisco");
		usuario.setAtivo(true);
		usuario.setPassword("123456");
		usuario.setConfirmPassword("123456");
		usuario.setEmail("francisco@servidor2.com.br");
		
		usuario = usuarioService.save(usuario);
        
		
	}

	
	
	
	
	
	
	
	
	

}
