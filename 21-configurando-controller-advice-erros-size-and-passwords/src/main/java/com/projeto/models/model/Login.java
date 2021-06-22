package com.projeto.models.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Login {
	
	private String email;
	private String password;
	
	public Login() {

	}

	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	@Email(message = "E-mail inválido")
	@NotBlank(message = "O e-mail do usuário deve ser informado. ")
    @NotEmpty(message = "O e-mail do usuário não pode ser em branco. ") 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    @NotEmpty(message = "A senha do usuário deve ser informada.") 
	@NotBlank(message = "A senha do usuário não pode ser em branco.")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + "]";
	}
	
	

}
