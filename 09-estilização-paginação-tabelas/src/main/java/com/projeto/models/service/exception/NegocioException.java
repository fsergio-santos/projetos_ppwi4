package com.projeto.models.service.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = -7579118257861080765L;
	
	public NegocioException(String mensagem) {
		super(mensagem);
	}

}
