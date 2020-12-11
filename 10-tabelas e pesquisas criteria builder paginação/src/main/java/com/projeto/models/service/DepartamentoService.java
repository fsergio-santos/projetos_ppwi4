package com.projeto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.models.model.Departamento;
import com.projeto.models.model.Usuario;
import com.projeto.models.repository.DepartamentoRepository;

@Service
@Transactional
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;
	
		
	public Departamento save(Departamento departamento) {
		return departamentoRepository.save(departamento);
	}
	
	public Departamento update(Departamento departamento) {
		return save(departamento);
	}
	
	public void deleteById(Long id) {
		departamentoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Departamento findById(Long id) {
		return departamentoRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Departamento> findAll(){
		return departamentoRepository.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public Page<Departamento> findAll(String search, Pageable pageable){
		return departamentoRepository.findAll(search, pageable);
	}
}
