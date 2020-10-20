package com.example.bancodigital.storage;

import com.example.bancodigital.controller.cpffile.StorageFileException;
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
import java.util.Objects;

@Service
public class StorageService {
  private final Path fileStorageLocation;

  @Autowired
  public StorageService(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    try{
      Files.createDirectories(this.fileStorageLocation);
    }catch (Exception e) {
      throw new StorageFileException("Não foi possível criar diretório.");
    }
  }

  public String store(MultipartFile file) {
    String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    try{
      if (filename.contains("..")) {
        throw new StorageFileException("Path inválido.");
      }
      Path targetLocation = this.fileStorageLocation.resolve(filename);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }catch (IOException e) {
      throw new StorageFileException("Falha ao arquivar " + filename);
    }
    return filename;
  }

  public Resource loadAsResource(String filename) {
    try{
      Path filePath = this.fileStorageLocation.resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw  new StorageFileException("Arquivo não encontrado " + filename);
      }
    } catch (MalformedURLException e) {
      throw  new StorageFileException("Arquivo não encontrado " + filename + " " + e.getMessage());
    }
  }
}
