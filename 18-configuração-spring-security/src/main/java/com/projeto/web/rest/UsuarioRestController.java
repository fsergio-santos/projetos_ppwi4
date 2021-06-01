package com.projeto.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.Usuario;
import com.projeto.models.model.dto.UsuarioDTO;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.assembler.UsuarioDTOAssembler;

@RestController
@RequestMapping(value="/rest/usuario")
public class UsuarioRestController {

    @Autowired
	private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioDTOAssembler usuarioAssembler;
    
    @ResponseBody
    @GetMapping(value="/listar")
    public Page<UsuarioDTO> findAll(
    		@RequestParam(value="paginaAtual", required = false) Optional<Integer> paginaAtual,
			@RequestParam(value="pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value="dir", required = false) Optional<String> dir,
			@RequestParam(value="props", required = false) Optional<String> props){

    	    Pageable pageable = gerarPagina( paginaAtual.orElse(0), pageSize.orElse(5), dir, props);
		
    	    Page<Usuario> listaUsuario = usuarioService.findAll(pageable);
    	    
    	    List<UsuarioDTO> usuariosDTO = usuarioAssembler.toCollectionDTO(listaUsuario.getContent());
    	    
    	    Page<UsuarioDTO> paginaUsuarioDTO = new PageImpl<>(usuariosDTO, pageable, listaUsuario.getTotalElements());
    	    
    	
    	return paginaUsuarioDTO;
    	
    }
    
    
    @ResponseBody
    @GetMapping(value="/listar/{nome}")
    public Page<Usuario> findUserByName(
    		@PathVariable("nome") String nome,
    		@RequestParam(value="paginaAtual", required = false) Optional<Integer> paginaAtual,
			@RequestParam(value="pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value="dir", required = false) Optional<String> dir,
			@RequestParam(value="props", required = false) Optional<String> props){
    	    
    	    Pageable pageable = gerarPagina( paginaAtual.orElse(0), pageSize.orElse(5), dir, props);
		
    	    Page<Usuario> listaUsuario = usuarioService.findUserByName(nome, pageable);
    	
    	    
    	return listaUsuario;
    	
    }
    
    @ResponseBody
    @GetMapping("/buscar/{id}")
    public Usuario findUserById(@PathVariable("id") Long id) {
    	return usuarioService.findUserById(id);
    }
    
    
    @ResponseBody
    @PostMapping(value="/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody @Valid Usuario usuario) {
    	System.out.println(usuario.toString());
    	usuarioService.save(usuario);
    }
    
    @ResponseBody
    @PostMapping(value="/alterar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody @Valid Usuario usuario) {
       	System.out.println(usuario.toString());
    	usuarioService.save(usuario);
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
