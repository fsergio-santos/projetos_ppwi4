package com.projeto.models.service.exception;

public class SenhaDiferenteConfirmeSenhaException extends NegocioException {

	private static final long serialVersionUID = -6301401645262722981L;

	public SenhaDiferenteConfirmeSenhaException(String mensagem) {
		super(mensagem);
	}

}
