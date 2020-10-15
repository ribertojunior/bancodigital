package com.example.bancodigital.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Endereco {
  private @Id
  @GeneratedValue
  Long id;

  @NonNull private Long cliente_id;
  @NonNull private String cep;
  @NonNull private String rua;
  @NonNull private String bairro;
  @NonNull private String complemento;
  @NonNull private String cidade;
  @NonNull private String estado;
}
