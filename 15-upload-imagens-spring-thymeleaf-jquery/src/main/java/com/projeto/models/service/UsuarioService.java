package com.projeto.models.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.UsuarioRepository;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;
import com.projeto.models.service.exception.IdNaoPodeSerZeroNuloException;
import com.projeto.models.service.exception.EntidadeNaoCadastradaException;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
		
	public Usuario save(Usuario usuario) {
		
		Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
		
		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)){
			throw new EmailCadastradoException("O e-mail já está cadastrado");
		}
	
		if ( usuario.getConfirmPassword().equals("")) {
			throw new ConfirmeSenhaNaoInformadoException("O campo confirme Senha deve ser preenchido");
		}
		
		if (usuario.getFoto().isEmpty()) {
			usuario.setFoto("default-avatar.png");
		}
		
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		return save(usuario);
	}
	
	public void deleteById(Long id) {
		findById(id);
		usuarioRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		if (id <=0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário é inválido!");
		}
		return usuarioRepository.findUsuarioById(id)
				.orElseThrow(()-> new EntidadeNaoCadastradaException("Usuário não cadastrado!"));
	}
	
	
	@Transactional(readOnly = true)
	public Usuario findUserById(Long id) {
		if (id <=0) {
			throw new IdNaoPodeSerZeroNuloException("Identificador do Usuário é inválido!");
		}
		return usuarioRepository.findUsuarioById(id)
				.orElseThrow(()-> new EntidadeNaoCadastradaException("Usuário não cadastrado!"));
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> findUsuarioById(Long id){
		return usuarioRepository.findById(id);
	}
	

	@Transactional(readOnly = true)
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> findUsuarioByEmail(String email){
		return usuarioRepository.findUsuarioByEmail(email);
	}

	
	public Page<Usuario> listUsuarioByPage(UsuarioFiltro usuarioFiltro, Pageable pageable){
		return usuarioRepository.listUsuarioByPage(usuarioFiltro, pageable);
	}
	

}
