package com.projeto.models.service.exception;

public class EntidadeNaoCadastradaException extends NegocioException {


	private static final long serialVersionUID = -8505945449296729975L;

	public EntidadeNaoCadastradaException(String mensagem) {
		super(mensagem);
	}

}
