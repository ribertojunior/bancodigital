package com.example.bancodigital;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.entity.Endereco;
import com.example.bancodigital.repository.ClienteRepository;
import com.example.bancodigital.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
    return args -> {
      Cliente cliente = clienteRepository.save(
          new Cliente(
              "Riberto",
              "Junior",
              "ribertojunior@gmail.com",
              new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"),
              "345.021.688-07"));
      log.info(
          "Preloading "
              + cliente);
      log.info("Preloading " +
          enderecoRepository.save(
              new Endereco(cliente.getId(),
                  "01540-040",
                  "Miguel Telles Junior",
                  "Cambuci",
                  "85A",
                  "Sao Paulo", "SP")));
    };
  }
}
