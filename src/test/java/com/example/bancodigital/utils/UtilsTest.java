package com.example.bancodigital.utils;

import com.example.bancodigital.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;


class UtilsTest {
  Cliente clienteValido;
  Cliente clienteNomeVazio;
  Cliente clienteSobrenomeVazio;
  Cliente clienteEmailRuim;
  Cliente clienteEmailRuim2;
  Cliente clienteCPFFalso;
  @BeforeEach
  void setUp() {
    try {
      clienteValido = new Cliente("Nome", "Sobrenome", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteNomeVazio = new Cliente("", "Sobrenome", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteSobrenomeVazio = new Cliente("Nome", "", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteEmailRuim = new Cliente("", "Sobrenome", "nome@.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteEmailRuim2 = new Cliente("", "Sobrenome", "nomenome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteCPFFalso = clienteEmailRuim = new Cliente("", "Sobrenome", "nome@.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.600.07");
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void valida() {
    assertTrue(Utils.valida(clienteValido));
    assertFalse(Utils.valida(clienteNomeVazio));
    assertFalse(Utils.valida(clienteSobrenomeVazio));
    assertFalse(Utils.valida(clienteEmailRuim));
    assertFalse(Utils.valida(clienteEmailRuim2));
    assertFalse(Utils.valida(clienteCPFFalso));

  }

  @Test
  void validaCPF() {
  }

  @Test
  void validaEmail() {
  }
}