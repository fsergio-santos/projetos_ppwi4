package com.projeto.models.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.models.model.Role;
import com.projeto.models.model.Usuario;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query("SELECT r FROM Role r WHERE r.id =:id")
	Optional<Role> findRoleById(@Param("id") Long id);

	@Query("SELECT r FROM Role r "
			+ "WHERE r.nome LIKE(CONCAT ('%', :nome, '%'))")
	Page<Role> findRoleByName(@Param("nome") String nome, Pageable pageable);
}
