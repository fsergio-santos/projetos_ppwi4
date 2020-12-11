package com.projeto.models.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.filtros.UsuarioFiltro;

public interface UsuarioQuery {

	Optional<Usuario> findUsuarioByEmail(String email);
	
	Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable);
	
}
