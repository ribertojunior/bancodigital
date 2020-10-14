package com.example.bancodigital.controller.cliente;

public class ClienteNotFoundException extends RuntimeException{
  ClienteNotFoundException(Long id) {
    super("Cliente não encontrado " + id);
  }
}
