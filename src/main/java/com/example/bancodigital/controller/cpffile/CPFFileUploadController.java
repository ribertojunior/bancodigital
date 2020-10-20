package com.example.bancodigital.controller.cpffile;

import com.example.bancodigital.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
public class CPFFileUploadController {
  @Autowired
  private final StorageService storageService;

  public CPFFileUploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @PostMapping("/cpfUpload")
  public UploadFileResponse handleFielUpload(@RequestParam("file") MultipartFile file) {
    String filename = storageService.store(file);
    String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
          .path("/files/")
          .path(filename)
          .toUriString();


    return new UploadFileResponse(filename, fileUri, file.getContentType(), file.getSize());
  }

  @ExceptionHandler(StorageFileException.class)
  public ResponseEntity<?> handleStorageFileNotDound(StorageFileException e) {
    return  ResponseEntity.notFound().build();
  }
}
