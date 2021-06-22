package com.projeto.models.service.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageRequestConfig {

	
	public static Pageable gerarPagina(Integer page, Integer limite, String direcao, String atributo) {
		return PageRequest.of(page, limite, getDirection(direcao), atributo);
	}
	
	private static Direction getDirection(String dir) {
		return dir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
}
