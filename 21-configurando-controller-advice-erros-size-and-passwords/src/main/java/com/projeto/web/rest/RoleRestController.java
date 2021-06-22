package com.projeto.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Role;
import com.projeto.models.service.RoleService;
import com.projeto.models.service.pagination.PageRequestConfig;

@RestController
@RequestMapping(value="/rest/role")
public class RoleRestController {
	
	@Autowired
	private RoleService roleService; 
	
    @ResponseBody
	@GetMapping(value="/listar")
	public Page<Role> findAll(
			@RequestParam(value="paginaAtual", required = false) Optional<Integer> paginaAtual,
			@RequestParam(value="pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value="dir", required = false) Optional<String> dir,
			@RequestParam(value="props", required = false) Optional<String> props){
    	
    	Pageable pageable = PageRequestConfig.gerarPagina(paginaAtual.orElse(0), 
					pageSize.orElse(10), 
					dir.orElse("asc"), 
					props.orElse("id"));
    	
    	Page<Role> paginasRoles = roleService.findAll(pageable);
    	
    	return paginasRoles;
	}
    
    
    @ResponseBody
	@GetMapping("/listar/{name}")
	public Page<Role> listarUsuarioPorNome(
			@PathVariable("name") String name, 
			@RequestParam(value="paginaAtual", required = false) Optional<Integer> paginaAtual,
			@RequestParam(value="pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value="dir", required = false) Optional<String> dir,
			@RequestParam(value="props", required = false) Optional<String> props){
    	
    	Pageable pageable = PageRequestConfig.gerarPagina(paginaAtual.orElse(0), 
					pageSize.orElse(10), 
					dir.orElse("asc"), 
					props.orElse("id"));
		
		Page<Role> listaRole = roleService.findAllByName(name, pageable);
		
		return  listaRole;
	}
   
}
