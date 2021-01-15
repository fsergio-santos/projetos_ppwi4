package com.projeto.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.models.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{

	@Query("SELECT d FROM Departamento d WHERE d.nome LIKE LOWER(CONCAT ('%', :name, '%'))")
	Page<Departamento> findAll(@Param("name") String name, Pageable pageable);
}
