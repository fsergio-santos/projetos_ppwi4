package com.projeto.models.service.exception;

@SuppressWarnings("serial")
public class UsernameNotFoundException extends NegocioException {

	public UsernameNotFoundException(String mensagem) {
		super(mensagem);
	}

}
