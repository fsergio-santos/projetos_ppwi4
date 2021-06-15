package com.projeto.models.model.dto;

import java.util.Date;

public class UsuarioDTO {
	
	private Long    id;
	private String  username;
	private String  password;
	private String  confirmPassword;
	private String  email;
	private boolean ativo = Boolean.FALSE;
	private Integer failedLogin = 0;
	private Date    lastLogin;
	private String  foto;
	private String  contentType;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public Integer getFailedLogin() {
		return failedLogin;
	}
	public void setFailedLogin(Integer failedLogin) {
		this.failedLogin = failedLogin;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	} 
	
	
	

}
