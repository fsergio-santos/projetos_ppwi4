package com.projeto.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Role;
import com.projeto.models.service.RoleService;

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

    	    Pageable pageable = gerarPagina( paginaAtual.orElse(0), pageSize.orElse(5), dir, props);
		
    	    Page<Role> listarRoles = roleService.findAll(pageable);
    	
    	return listarRoles;
    	
    }
	
	private Pageable gerarPagina(Integer paginaAtual, Integer pageSize, Optional<String> dir, Optional<String> props) {
       	return PageRequest.of(paginaAtual, pageSize, getDirecao(dir) , getAtributte(props));
    }
    
    private String getAtributte(Optional<String> props) {
    	return props.orElse("id");
    }
    
    private Direction getDirecao(Optional<String> dir) {
    	return dir.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
	
	
}
