package com.example.bancodigital.controller;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClienteController {
  private final ClienteRepository clienteRepository;

  @GetMapping("/clientes")
  List<Cliente> all() {
    return clienteRepository.findAll();
  }

  @PostMapping("/clientes")
  Cliente newCliente(@RequestBody Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @GetMapping("/clientes/{id}")
  Cliente one(@PathVariable Long id) {
    return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
  }

  @PutMapping("/clientes/{id}")
  Cliente replaceCliente(@RequestBody Cliente novoCliente, @PathVariable Long id) {
    return clienteRepository.findById(id).map(cliente -> {
      cliente.setNome(novoCliente.getNome());
      cliente.setCpf(novoCliente.getCpf());
      cliente.setDataDeNascimento(novoCliente.getDataDeNascimento());
      cliente.setEmail(novoCliente.getEmail());
      cliente.setSobrenome(novoCliente.getSobrenome());
      return clienteRepository.save(cliente);
    }).orElseGet(() -> {
      novoCliente.setId(id);
      return clienteRepository.save(novoCliente);
    });
  }

  @DeleteMapping("/clientes/{id}")
  void deleteCliente(@PathVariable Long id) {
    clienteRepository.deleteById(id);
  }
}
