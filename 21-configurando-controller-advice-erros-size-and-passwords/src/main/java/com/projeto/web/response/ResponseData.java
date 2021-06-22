package com.projeto.web.response;

public class ResponseData {

	private String id;
	private String nome;
	
	public ResponseData(String id, String text) {
		super();
		this.id = id;
		this.nome = text;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
