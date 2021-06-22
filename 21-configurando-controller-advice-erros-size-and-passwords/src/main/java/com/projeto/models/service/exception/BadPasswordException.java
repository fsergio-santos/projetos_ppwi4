package com.projeto.models.service.exception;

public class BadPasswordException extends NegocioException {


	private static final long serialVersionUID = 6021441074185587614L;

	public BadPasswordException(String mensagem) {
		super(mensagem);
		
	}

}
