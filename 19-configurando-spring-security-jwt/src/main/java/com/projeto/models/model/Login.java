package com.projeto.models.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Login {
	
	private String email;
	private String password;
	
	public Login() {}
	
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Email
	@NotEmpty
	@NotBlank
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty
	@NotBlank
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	

}
