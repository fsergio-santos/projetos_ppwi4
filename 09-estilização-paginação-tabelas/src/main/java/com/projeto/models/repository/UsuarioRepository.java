package com.projeto.models.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>, UsuarioQuery {
	
	
}
