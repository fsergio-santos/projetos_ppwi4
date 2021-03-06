package com.projeto.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.models.model.Usuario;
import com.projeto.models.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
		
	public Usuario save(Usuario usuario) {
		
		Optional<Usuario> usuarioCadastrado = findUsuarioByEmail(usuario.getEmail());
		
		if (usuarioCadastrado.isPresent() && !usuarioCadastrado.get().equals(usuario)){
			throw new RuntimeException("O e-mail já está cadastrado");
		}
	
		if ( usuario.getConfirmPassword().equals("")) {
			throw new RuntimeException("O campo confirme Senha deve ser preenchido");
		}
	
		System.out.println(" senha "+usuario.getConfirmPassword());
		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuario) {
		System.out.println("passando pelo update");
		return save(usuario);
	}
	
	public void deleteById(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}
	
	
	public Optional<Usuario> findUsuarioByEmail(String email){
		return usuarioRepository.findUsuarioByEmail(email);
	}
	
	
	

}
