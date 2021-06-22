package com.projeto.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.projeto.models.model.Departamento;
import com.projeto.models.service.DepartamentoService;

@Component
public class DepartamentoConverter implements Converter<String, Departamento>{

	@Autowired
	private DepartamentoService departamentoService;
	
	@Override
	public Departamento convert(String source) {
	   if (source.isEmpty()) {
    	   return null;
       }
       Long id = Long.valueOf(source);
       return departamentoService.findById(id);
	
	}

}
