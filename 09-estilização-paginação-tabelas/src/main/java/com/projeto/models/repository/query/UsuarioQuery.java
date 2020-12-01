package com.projeto.models.repository.query;

import java.util.Optional;

import com.projeto.models.model.Usuario;

public interface UsuarioQuery {

	Optional<Usuario> findUsuarioByEmail(String email);
	
}
