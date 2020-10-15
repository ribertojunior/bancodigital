package com.example.bancodigital.controller.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClienteRepetidoAdvice {

  @ResponseBody
  @ExceptionHandler(ClienteRepetidoException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String clienteRepetidoHandle(ClienteRepetidoException e) { return e.getMessage();}
}
