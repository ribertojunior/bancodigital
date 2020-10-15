package com.example.bancodigital.controller.endereco;

public class EnderecoNotFoundException extends RuntimeException {
  public EnderecoNotFoundException(Long id) {
    super("Endereço não encontrado: " + id);
  }
}
