package com.projeto.web.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.dto.Foto;
import com.projeto.models.model.dto.FotoRequest;
import com.projeto.models.service.LocalFotosStorageService;
import com.projeto.models.service.exception.FileStorageException;

@RestController
@RequestMapping(value="/foto")
public class FotoRestController {
	
	@Autowired
	private LocalFotosStorageService localFotosStorageService;
	
	@PostMapping(value="/gravar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
								 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Foto> uploadFoto(FotoRequest fotoRequest) {
        Long id = 0L; 
        if ( !"".equals(fotoRequest.getId())) {
        	id = Long.valueOf(Long.valueOf(fotoRequest.getId()));
		}
		Foto arquivoFoto = new Foto();
		try {
			arquivoFoto.setId(id);
			arquivoFoto.setNomeArquivo(fotoRequest.getFoto().getOriginalFilename());
			arquivoFoto.setInputStream(fotoRequest.getFoto().getInputStream());
			arquivoFoto.setContentType(fotoRequest.getFoto().getContentType());
			arquivoFoto = localFotosStorageService.armazenar(arquivoFoto);
		} catch (IOException e) {
			throw new FileStorageException("Falha na gravação do arquivo de foto", e);
		}
		return ResponseEntity.ok().body(arquivoFoto);
	}
	
	
	@PostMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FotoRequest> excluirFoto(@RequestBody FotoRequest fotoRequest) {
		Long id = 0L;
		if ( !"".equals(fotoRequest.getId())) {
	       	id = Long.valueOf(Long.valueOf(fotoRequest.getId()));
		}
		Foto arquivoFoto = new Foto();
		arquivoFoto.setId(id);
		arquivoFoto.setNomeArquivo(fotoRequest.getNomeArquivo());
		arquivoFoto = localFotosStorageService.excluirFoto(arquivoFoto);
		fotoRequest.setNomeArquivo(arquivoFoto.getNomeArquivo());
		return ResponseEntity.ok().body(fotoRequest);
    }
	
	
	@GetMapping("/{nomeFoto}")
	public byte[] recuperarFoto(@PathVariable String nomeFoto) {
		return localFotosStorageService.recuperarFoto(nomeFoto);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
