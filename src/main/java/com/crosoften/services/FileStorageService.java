package com.crosoften.services;


import com.crosoften.configuration.FileStorageProperties;
import com.crosoften.exception.FileStorageException;
import com.crosoften.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get( fileStorageProperties.getUploadDir() ).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories( this.fileStorageLocation );
		} catch (Exception e) {
			throw new FileStorageException( "Não foi possível criar o diretório dos arquivos.", e );
		}
	}
	
	public String storeFile(MultipartFile multipartFile) {
		String fileName = StringUtils.cleanPath( multipartFile.getOriginalFilename() );
		
		try {
			if (fileName.contains( ".." ))
				throw new FileStorageException( "Arquivo " + multipartFile + " não contém uma extensão válida " );
			
			Path targetLocation = this.fileStorageLocation.resolve( fileName );
			Files.copy( multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );
			
			return fileName;
			
		} catch (IOException e) {
			
			throw new FileStorageException( "Erro ao salvar o arquivo " + fileName + ". Por gentileza, tente novamente! ", e );
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			
			Path filePath = this.fileStorageLocation.resolve( fileName ).normalize();
			Resource resource = new UrlResource( filePath.toUri() );
			
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException( "Arquivo não encontracdo " + fileName );
			}
			
		} catch (MalformedURLException e) {
			throw new MyFileNotFoundException( "Arquivo não encontracdo " + fileName, e );
		}
	}
	
}
