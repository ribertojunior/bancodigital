package com.example.bancodigital.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Cliente {

  private @Id
  @GeneratedValue Long id;
  private String nome;

  private String sobrenome;

  private String email;

  private Date dataDeNascimento;

  private String cpf;

  public Cliente() {}

  public Cliente(String nome, String sobrenome, String email, Date dataDeNascimento, String cpf) {
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.dataDeNascimento = dataDeNascimento;
    this.cpf = cpf;
  }
}
