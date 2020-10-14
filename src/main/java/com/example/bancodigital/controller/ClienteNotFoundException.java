package com.example.bancodigital.controller;

public class ClienteNotFoundException extends RuntimeException{
  ClienteNotFoundException(Long id) {
    super("Cliente não encontrado " + id);
  }
}
