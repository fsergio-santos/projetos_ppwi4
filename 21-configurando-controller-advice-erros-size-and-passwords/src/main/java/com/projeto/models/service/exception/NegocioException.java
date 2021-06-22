package com.projeto.models.service.exception;

public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 1922065289043435562L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	} 
	
	
}
