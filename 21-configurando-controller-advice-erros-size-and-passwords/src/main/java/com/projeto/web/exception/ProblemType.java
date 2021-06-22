package com.projeto.web.exception;

public enum ProblemType {

	ERRO_NEGOCIO("/erro-negocio","Violação de regra de negócio"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","O Recurso solicitado não foi localizado"),
	ENTIDADE_EM_USO("/entidade-em-uso","A Entidade está em uso no sistema"), 
	DADOS_INVALIDOS("/dados-invalidos","Dados inválidos"),
	SERVICO_EMAIL("/erro-servidor-email","Erro no serviço de E-mail"),
	TOKEN_INVALIDO("/token-invalido","O token está inválido ou está expirado");
	
	private String uri;
	private String title;
	
	private ProblemType(String uri, String title) {
	    this.uri = uri;
	    this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public String getTitle() {
		return title;
	}
	
	
	
	
}
