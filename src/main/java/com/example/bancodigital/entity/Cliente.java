package com.example.bancodigital.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Cliente {

  private @Id
  @GeneratedValue Long id;
  @NonNull private String nome;
  @NonNull private String sobrenome;
  @NonNull private String email;
  @NonNull private Date dataDeNascimento;
  @NonNull private String cpf;
  @ManyToOne(targetEntity = Endereco.class)
  @JoinColumn(name = "endereco_id", referencedColumnName = "id")
  private Endereco endereco;

  /**
   * Construtor Cliente
   * @param nome nome do cliente
   * @param sobrenome do cliente
   * @param email do cliente
   * @param dataDeNascimento do cliente
   * @param cpf do cliente
   * @param endereco do cliente (pode ser nulo)
   */
  public Cliente(String nome, String sobrenome, String email, Date dataDeNascimento, String cpf, Endereco endereco) {
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.dataDeNascimento = dataDeNascimento;
    this.cpf = cpf;
    this.endereco = endereco;
  }
}
