package com.projeto.models.repository.pagination;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.projeto.models.config.ConfigProjeto;

public class PageRequestConfig {

	
	public static Pageable requestPage(Optional<Integer> size, Optional<Integer> page, Optional<String> sort,
			Optional<String> dir) {
		Pageable pageable = PageRequest.of(page.orElse(ConfigProjeto.INITIAL_PAGE), 
				  						   size.orElse(ConfigProjeto.SIZE),
				  						   getDirection(dir), getAtributte(sort));
		return pageable;
	}
	
	private static String getAtributte(Optional<String> sort) {
		return sort.orElse("id");
	}


	private static Direction getDirection(Optional<String> dir) {
		String direcao = dir.orElse("asc");
		return direcao.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	}
}
