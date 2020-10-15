package com.example.bancodigital.repository;

import com.example.bancodigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByEmail(String email);
  Cliente findByCpf(String cpf);
}
