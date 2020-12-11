package com.projeto.models.repository;



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
	
	@Query("SELECT u FROM Usuario u WHERE u.username LIKE LOWER(CONCAT ('%', :name, '%'))")
	Page<Usuario> findAll(@Param("name") String name, Pageable pageable);
	
}
