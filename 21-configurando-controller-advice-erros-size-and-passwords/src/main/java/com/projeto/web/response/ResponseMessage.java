package com.projeto.web.response;

public class ResponseMessage {

	private String mensagem; 
	private int status;
	
	public ResponseMessage(String mensagem, int status) {
		this.mensagem = mensagem;
		this.status = status;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
