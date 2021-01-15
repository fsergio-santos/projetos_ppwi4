package com.projeto.models.model.dto;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Foto {
	
	private String nomeArquivo;
	
	@JsonIgnore
	private InputStream inputStream;
	
	private String contentType;
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return "Foto [nomeArquivo=" + nomeArquivo + ", inputStream=" + inputStream + ", contentType=" + contentType
				+ ", id=" + id + "]";
	}
	

}
