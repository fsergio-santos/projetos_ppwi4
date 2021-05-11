package com.projeto.models.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.model.Usuario;
import com.projeto.models.model.dto.Foto;
import com.projeto.models.service.exception.FileStorageException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Service
public class LocalFotosStorageService {

	private Path diretorioFotos;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	public LocalFotosStorageService() {
		try {
            diretorioFotos = Paths.get(ConfigProjeto.DIRETORIO_FOTOS).toAbsolutePath().normalize();  
			Files.createDirectories(diretorioFotos);
		} catch (IOException e) {
			throw new FileStorageException("Não foi possível criar diretórios de fotos ", e);
		}
	}

    


	public Foto armazenar(Foto foto) {
		String nomeFoto = null;
		Usuario usuario = buscarUsuario(foto.getId());
		if (!Objects.isNull(usuario)) {
			 remover(usuario.getFoto());
		}
		nomeFoto = gerarNomeArquivo(foto.getNomeArquivo());
		try {
			Path arquivoPath = getArquivoPath(nomeFoto);
			FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
			Thumbnails.of(arquivoPath.toString()).size(50, 78).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
			foto.setNomeArquivo(nomeFoto);
			return foto;
		} catch (IOException e) {
			throw new FileStorageException("Erro na gravação da foto ", e);
		}
	}


	private Usuario buscarUsuario(Long id ) {
		Usuario usuario = null;
		if ( id != 0L) {
			usuario = usuarioService.findUserById(id);
		}
		return usuario;
	}
	
	
	public Foto excluirFoto(Foto foto) {
		String nomeFoto = null;
		Usuario usuario = buscarUsuario(foto.getId());
		nomeFoto = Objects.isNull(usuario) ? foto.getNomeArquivo() : usuario.getFoto();
		if (remover(nomeFoto)) {
			foto.setNomeArquivo("default-avatar.png");
		};
		return foto;
	}

	public boolean remover(String foto) {
		String thumbnail = "thumbnail."+foto;
        if (!foto.isEmpty()) { 
			try {
				
				Path arquivoThumbinailPath = getArquivoPath(thumbnail);
				Files.deleteIfExists(arquivoThumbinailPath);
				
				Path arquivoPath = getArquivoPath(foto);
				Files.deleteIfExists(arquivoPath);
				
				return true;
			} catch (IOException e) {
				throw new FileStorageException("Erro na exclusão da foto", e);
			}
		}
        return false;
	}

	public InputStream recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			return Files.newInputStream(arquivoPath);
		} catch (IOException e) {
			throw new FileStorageException("Erro na recuperação da foto ", e);
		}
	}

	public byte[] recuperarFoto(String nomeArquivo) {
		try {
			return Files.readAllBytes(getArquivoPath(nomeArquivo));
		} catch (IOException e) {
			throw new FileStorageException("Erro na leitura da foto ", e);
		}
	}

	public String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Paths.get(nomeArquivo));
	}

}
