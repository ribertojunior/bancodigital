package com.example.bancodigital.utils;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.entity.Endereco;

import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Utils {

  public static String valida(Cliente cliente) {
    String retorno =
        cliente.getNome() != null
                && !cliente.getNome().trim().isEmpty()
                && cliente.getSobrenome() != null
                && !cliente.getSobrenome().trim().isEmpty()
            ? ""
            : " Nome ou sobrenome vazio.";
    return retorno
        + validaEmail(cliente.getEmail())
        + validaCPF(cliente.getCpf())
        + validaData(cliente.getDataDeNascimento());
  }

  public static String validaData(Date dataDeNascimento) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, -18);
    return dataDeNascimento != null && dataDeNascimento.before(calendar.getTime())
        ? ""
        : "Data de nascimento inválida";
  }

  public static String validaCPF(String cpf) {
    if (cpf == null) return "CPF vazio.";
    cpf = cpf.replace(".", "").replace("-", "");
    if (cpf.equals("00000000000")
        || cpf.equals("11111111111")
        || cpf.equals("22222222222")
        || cpf.equals("33333333333")
        || cpf.equals("44444444444")
        || cpf.equals("55555555555")
        || cpf.equals("66666666666")
        || cpf.equals("77777777777")
        || cpf.equals("88888888888")
        || cpf.equals("99999999999")
        || (cpf.length() != 11)) return " CPF inválido.";
    char dig10, dig11;
    int sm, i, r, num, peso;
    try {
      sm = 0;
      peso = 10;
      for (i = 0; i < 9; i++) {
        num = (cpf.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }
      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11)) dig10 = '0';
      else dig10 = (char) (r + 48);
      sm = 0;
      peso = 11;
      for (i = 0; i < 10; i++) {
        num = (cpf.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11)) dig11 = '0';
      else dig11 = (char) (r + 48);
      return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)) ? "" : " CPF inválido.";
    } catch (InputMismatchException erro) {
      return "CPF inválido.";
    }
  }

  public static String validaEmail(String email) {
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

    return email != null && !email.trim().isEmpty() && pattern.matcher(email).matches()
        ? ""
        : " Email não válido.";
  }

  public static String validaEndereco(Endereco endereco) {
    return endereco != null
        ? validaCEP(endereco.getCep()) + validaString(endereco.getRua(), "Rua")
        + validaString(endereco.getBairro(), "Bairro")
        + validaString(endereco.getComplemento(), "Complemento")
        + validaString(endereco.getCidade(), "Cidade")
        + validaString(endereco.getEstado(), "Estado")
        : "Endereço vazio.";
  }

  public static String validaString(String s, String desc) {
    return s != null && !s.trim().isEmpty() ? "" : "Campo " + desc + " é obrigatório.";
  }

  public static String validaCEP(String cep) {
    return cep != null && !cep.trim().isEmpty() && cep.matches("\\d{5}-\\d{3}")
        ? ""
        : "CEP inválido";
  }
}
