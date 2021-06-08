package com.projeto.models.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.query.UsuarioQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>, UsuarioQuery {
	       
	@Query("SELECT u FROM Usuario u "
		   +"LEFT JOIN FETCH u.roles "
		   +"LEFT JOIN FETCH u.departamento "
		   + "WHERE u.id =:id"	)
	Optional<Usuario> findUsuarioById(@Param("id") Long id);

	
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.username LIKE(CONCAT ('%', :nome, '%'))")
	Page<Usuario> findUserByName(@Param("nome") String nome, Pageable pageable);
	
	
}





