package com.projeto.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.models.model.Departamento;
import com.projeto.models.repository.DepartamentoRepository;
import com.projeto.models.service.exception.EntidadeNaoCadastradaException;
import com.projeto.web.response.ResponseData;
import com.projeto.web.response.ResponseSelect2Data;

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
	public Departamento findDepartamentoById(Long id) {
		return departamentoRepository.findDepartamentoById(id)
				.orElseThrow(()-> new EntidadeNaoCadastradaException("Departamento não cadastrado!"));
	}
	
	
	@Transactional(readOnly = true)
	public List<Departamento> findAll(){
		return departamentoRepository.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public Page<Departamento> findAll(String search, Pageable pageable){
		return departamentoRepository.findAll(search, pageable);
	}
	
	@Transactional(readOnly = true)
	public List<ResponseData> buscaPorParametroDepartamento(String query) {
    	return findAll().stream()
				  		.limit(15)
				  		.filter( d-> d.getNome()
				  				      .toLowerCase()
				  				      .contains(query.toString()
				  				    		         .toLowerCase()))
				  .map( d ->  { 
					  return departamentoToResponseSelect2Data(d); 
					  } 
				  )
				  .collect(Collectors.toList()); 	
	}


	@Transactional(readOnly = true)
	public List<ResponseData> buscaSemParamentro() {
		return findAll().stream()
						.limit(15)
						.map( d ->  { 
						    return departamentoToResponseSelect2Data(d); 
						})
						.collect(Collectors.toList()); 	
	}

	private ResponseData departamentoToResponseSelect2Data(Departamento d) {
           return new ResponseData(d.getId().toString(), d.getNome());		
	}

	
}
