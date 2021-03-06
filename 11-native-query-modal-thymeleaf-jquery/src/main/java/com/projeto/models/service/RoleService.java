package com.projeto.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.models.model.Role;
import com.projeto.models.repository.RoleRepository;

@Service
@Transactional
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	public Role update(Role role) {
		return save(role);
	}
	
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		return roleRepository.getOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	
}
