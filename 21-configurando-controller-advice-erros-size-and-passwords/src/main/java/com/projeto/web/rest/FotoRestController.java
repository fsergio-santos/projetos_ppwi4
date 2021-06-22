package com.projeto.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.model.dto.Foto;
import com.projeto.models.model.dto.FotoRequest;
import com.projeto.models.service.LocalFotosStorageService;
import com.projeto.models.service.exception.FileStorageException;

@RestController
@RequestMapping(value="/rest/foto")
public class FotoRestController {
	
	@Autowired
	private LocalFotosStorageService localFotosStorageService;
	
	@PostMapping(value="/gravar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
								  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FotoRequest> uploadFoto(FotoRequest fotoRequest) {
		
		System.out.println("passando por aqui "+ fotoRequest.toString());
		
	    Long id = convertIdUsuario(fotoRequest);
		
		Foto arquivoFoto = new Foto();
		
		try {
			arquivoFoto.setNomeArquivo(fotoRequest.getFoto().getOriginalFilename());
			arquivoFoto.setInputStream(fotoRequest.getFoto().getInputStream());
			arquivoFoto.setContentType(fotoRequest.getFoto().getContentType());
			arquivoFoto.setId(id);
			arquivoFoto = localFotosStorageService.armazenar(arquivoFoto);
			fotoRequest.setNomeArquivo(arquivoFoto.getNomeArquivo());
			fotoRequest.setContentType(arquivoFoto.getContentType());
			fotoRequest.setFoto(null);
		} catch (IOException e) {
			throw new FileStorageException("Falha na gravação do arquivo de foto", e);
		}
		
		return ResponseEntity.ok().body(fotoRequest);
	}
	
	
	@PostMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FotoRequest> excluirFoto(@RequestBody FotoRequest fotoRequest) {
		Long id = convertIdUsuario(fotoRequest);
		
		Foto foto = new Foto();
		foto.setNomeArquivo(fotoRequest.getNomeArquivo());
		foto.setId(id);
		foto = localFotosStorageService.excluirFoto(foto);
		fotoRequest.setNomeArquivo(foto.getNomeArquivo());
		return ResponseEntity.ok().body(fotoRequest);
    }

    @ResponseBody
	@GetMapping("/teste/{nomeFoto:.+}")
	public ResponseEntity<Resource> recuperarFoto(@PathVariable String nomeFoto, HttpServletRequest request) {

    	Resource resource = localFotosStorageService.loadFileAsResource(nomeFoto);
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@ResponseBody
    @GetMapping("/{nomeFoto:.+}")
	public byte[] recuperarFoto(@PathVariable String nomeFoto) {
		return localFotosStorageService.recuperarFoto(nomeFoto);
	}
    
	private Long convertIdUsuario(FotoRequest fotoRequest) {
		 return !"".equals(fotoRequest.getId()) ? Long.valueOf(fotoRequest.getId()) : 0L;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
