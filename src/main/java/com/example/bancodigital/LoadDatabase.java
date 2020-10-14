package com.example.bancodigital;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(ClienteRepository clienteRepository) {
    return args -> {
      log.info(
          "Preloading " +
      clienteRepository.save(new Cliente("Riberto", "Junior", "ribertojunior@gmail.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688-07")));
      log.info(
          "Preloading " + clienteRepository.save(new Cliente("Riberto Cesar", " Carmo Junior", "ribertojunior@gmail.com", new SimpleDateFormat("dd/MM/yyyy").parse("18/06/1987"), "345.021.688-07")));
    };
  }
}
