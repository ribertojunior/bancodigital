package com.example.bancodigital.utils;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.entity.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UtilsTest {
  Cliente clienteValido;
  Cliente clienteNomeVazio;
  Cliente clienteSobrenomeVazio;
  Cliente clienteEmailRuim;
  Cliente clienteEmailRuim2;
  Cliente clienteCPFFalso;
  Endereco endereco;
  Endereco enderecoCepInvalido;
  Endereco enderecoCepInvalido2;
  Endereco enderecoCepVazio;
  Endereco enderecoRuaVazia;

  @BeforeEach
  void setUp() {
    try {
      clienteValido = new Cliente("Nome", "Sobrenome", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteNomeVazio = new Cliente("", "Sobrenome", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteSobrenomeVazio = new Cliente("Nome", "", "nome@nome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteEmailRuim = new Cliente("", "Sobrenome", "nome@.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteEmailRuim2 = new Cliente("", "Sobrenome", "nomenome.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688.07");
      clienteCPFFalso = clienteEmailRuim = new Cliente("", "Sobrenome", "nome@.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.600.07");
      endereco = new Endereco(1L, "01540-040", "Rua do Rapaz", "Limoeiro", "Todos", "SP", "SP");
      enderecoCepInvalido = new Endereco(1L, "0154040", "Rua do Rapaz", "Limoeiro", "Todos", "SP", "SP");
      enderecoCepInvalido2 = new Endereco(1L, "0", "Rua do Rapaz", "Limoeiro", "Todos", "SP", "SP");
      enderecoCepVazio = new Endereco(1L, "", "Rua do Rapaz", "Limoeiro", "Todos", "SP", "SP");
      enderecoRuaVazia = new Endereco(1L, "01540-040", "", "Limoeiro", "Todos", "SP", "SP");
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void valida() {
    assertTrue(Utils.valida(clienteValido).trim().isEmpty());
    assertFalse(Utils.valida(clienteNomeVazio).trim().isEmpty());
    assertFalse(Utils.valida(clienteSobrenomeVazio).trim().isEmpty());
    assertFalse(Utils.valida(clienteEmailRuim).trim().isEmpty());
    assertFalse(Utils.valida(clienteEmailRuim2).trim().isEmpty());
    assertFalse(Utils.valida(clienteCPFFalso).trim().isEmpty());
  }

  @Test
  void validaCPF() {
  }

  @Test
  void validaEmail() {
  }

  @Test
  void validaData() {
    try {
      assertTrue(Utils.validaData(new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987")).trim().isEmpty());
      assertFalse(Utils.validaData(new SimpleDateFormat("dd/MM/yyyy").parse("18/06/2020")).trim().isEmpty());
      assertTrue(Utils.validaData(new SimpleDateFormat("dd/MM/yyyy").parse("14/10/2002")).trim().isEmpty());
      assertFalse(Utils.validaData(new SimpleDateFormat("dd/MM/yyyy").parse("15/10/2002")).trim().isEmpty());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void validaCEP() {
    assertTrue(Utils.validaCEP("01540-040").isEmpty());
    assertFalse(Utils.validaCEP("").isEmpty());
    assertFalse(Utils.validaCEP("000000000").isEmpty());
    assertFalse(Utils.validaCEP("000").isEmpty());
    assertFalse(Utils.validaCEP("01540040").isEmpty());
  }

  @Test
  void validaEndereco() {
    assertTrue(Utils.validaEndereco(endereco).trim().isEmpty());
    assertFalse(Utils.validaEndereco(enderecoCepInvalido).trim().isEmpty());
    assertFalse(Utils.validaEndereco(enderecoCepInvalido2).trim().isEmpty());
    assertFalse(Utils.validaEndereco(enderecoCepVazio).trim().isEmpty());
    assertFalse(Utils.validaEndereco(enderecoRuaVazia).trim().isEmpty());
  }
}