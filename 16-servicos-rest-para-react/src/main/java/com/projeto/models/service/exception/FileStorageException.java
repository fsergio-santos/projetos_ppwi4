package com.projeto.models.service.exception;

public class FileStorageException extends NegocioException {

	private static final long serialVersionUID = -8545897820308993032L;

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public FileStorageException(String mensagem) {
		super(mensagem);
	
	}

}
