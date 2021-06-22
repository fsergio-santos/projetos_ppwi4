package com.projeto.models.service.exception;

public class RoleNaoCadastradaException extends EntidadeNaoCadastradaException{

	private static final long serialVersionUID = -2743208788329205886L;

	public RoleNaoCadastradaException(String mensagem) {
		super(mensagem);
	}
	
	public RoleNaoCadastradaException(Long id) {
		this(String.format("Não existe um cadastro "
							+ "da Role com o código %d", id));
	}

}
