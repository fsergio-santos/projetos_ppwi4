package com.projeto.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.models.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query("SELECT r FROM Role r WHERE r.id =:id")
	Optional<Role> findRoleById(@Param("id") Long id);

}
