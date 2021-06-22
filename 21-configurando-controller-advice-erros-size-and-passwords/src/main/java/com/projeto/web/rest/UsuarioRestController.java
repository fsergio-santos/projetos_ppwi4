package com.projeto.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.projeto.models.service.pagination.PageRequestConfig;
import com.projeto.web.response.ResponseMessage;

@RestController
@RequestMapping(value = "/rest/usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;

	@ResponseBody
	@GetMapping(value = "/listar")
	public Page<UsuarioDTO> findAll(@RequestParam(value = "paginaAtual", required = false) Optional<Integer> paginaAtual,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
			@RequestParam(value = "dir", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {
		
		Pageable pageable = PageRequestConfig.gerarPagina(paginaAtual.orElse(0), pageSize.orElse(10), dir.orElse("asc"), props.orElse("id"));

		Page<Usuario> paginasUsuario = usuarioService.findAll(pageable);

		List<UsuarioDTO> usuarioDTO = usuarioAssembler.toCollectionDTO(paginasUsuario.getContent());

		Page<UsuarioDTO> paginaUsuarioDTO = new PageImpl<>(usuarioDTO, pageable, paginasUsuario.getTotalElements());
		
		return paginaUsuarioDTO;

	}

	@ResponseBody
	@GetMapping("/listar/{name}")
	public Page<Usuario> listarUsuarioPorNome(@PathVariable("name") String name,
			@RequestParam(value = "page", required = false) Optional<Integer> page,
			@RequestParam(value = "limit", required = false) Optional<Integer> limit,
			@RequestParam(value = "direction", required = false) Optional<String> dir,
			@RequestParam(value = "props", required = false) Optional<String> props) {

		
		Pageable pageable = PageRequestConfig.gerarPagina(page.orElse(0), limit.orElse(10), dir.orElse("asc"), props.orElse("id"));

		Page<Usuario> listaUsuario = usuarioService.findAllByName(name, pageable);

		return listaUsuario;
	}

	@ResponseBody
	@GetMapping("/buscar/{id}")
	public Usuario findUserById(@PathVariable("id") Long id) {
		return usuarioService.findUserById(id);

	}

	@PostMapping(value = "/inserir", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> saveUser(@RequestBody @Valid Usuario usuario) {
		usuarioService.save(usuario);
		return ResponseEntity.ok(new ResponseMessage("Usuário Registrado com sucesso!",200));
	}

	@PostMapping(value = "/alterar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> updateUser(@RequestBody @Valid Usuario usuario) {
		System.out.println(usuario.toString());
		usuarioService.save(usuario);
		return ResponseEntity.ok(new ResponseMessage("Usuário Alterado com sucesso!",200));
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
		usuarioService.deleteById(id);
		return ResponseEntity.ok("Usuário Excluído com Sucesso!");
	}

}
