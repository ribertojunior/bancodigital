package com.example.bancodigital.controller.cliente;

public class ClienteNotFoundException extends RuntimeException{
  public ClienteNotFoundException(Long id) {
    super("Cliente não encontrado " + id);
  }
}
