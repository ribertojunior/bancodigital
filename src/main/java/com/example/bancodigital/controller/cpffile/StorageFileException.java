package com.example.bancodigital.controller.cpffile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StorageFileException extends RuntimeException {
  public StorageFileException(String s) {
    super(s);
  }
}
