package com.projeto.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Departamento;
import com.projeto.models.service.DepartamentoService;
import com.projeto.web.response.ResponseSelect2Data;

@RestController
@RequestMapping(value="/rest/departamento")
public class DepartamentoRestController {
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@ResponseBody
	@GetMapping(value="/listar")
	public List<Departamento> listarDepartamento() {
		List<Departamento> departamentos = departamentoService.findAll();
		return departamentos;
	}  
	
	@ResponseBody
	@GetMapping(value="/buscaDepartamento")
	public List<ResponseSelect2Data> selectDepartamento(@RequestParam(value="q", required = false) String query){
		return StringUtils.isEmpty(query)
				       ? departamentoService.buscaSemParamentro() 
				       : departamentoService.buscaPorParametroDepartamento(query);
	}
	
	
	

}
