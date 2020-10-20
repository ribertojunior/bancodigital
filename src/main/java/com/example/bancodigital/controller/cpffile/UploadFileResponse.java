package com.example.bancodigital.controller.cpffile;

import lombok.Data;

@Data
public class UploadFileResponse {
  private String fileName;
  private String fileUri;
  private String contentType;
  private long size;

  public UploadFileResponse(String fileName, String fileUri, String contentType, long size) {
    this.fileName = fileName;
    this.fileUri = fileUri;
    this.contentType = contentType;
    this.size = size;
  }
}
