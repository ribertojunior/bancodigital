package com.example.bancodigital.controller;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class ClienteController {
  private final ClienteRepository clienteRepository;

  @GetMapping("/clientes")
  CollectionModel<EntityModel<Cliente>> all() {
    List<EntityModel<Cliente>> clientes =
        clienteRepository.findAll().stream().map(cliente -> EntityModel.of(cliente, linkTo(methodOn(ClienteController.class).one(cliente.getId())).withSelfRel(),
        linkTo(methodOn(ClienteController.class).all()).withRel("clientes"))).collect(Collectors.toList());
    return CollectionModel.of(clientes,
        linkTo(methodOn(ClienteController.class).all()).withSelfRel());
  }

  @PostMapping("/clientes")
  Cliente newCliente(@RequestBody Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @GetMapping("/clientes/{id}")
  EntityModel<Cliente> one(@PathVariable Long id) {
    Cliente cliente =
        clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    return EntityModel.of(
        cliente,
        linkTo(methodOn(ClienteController.class).one(id)).withSelfRel(),
        linkTo(methodOn(ClienteController.class).all()).withRel("clientes"));
  }

  @PutMapping("/clientes/{id}")
  Cliente replaceCliente(@RequestBody Cliente novoCliente, @PathVariable Long id) {
    return clienteRepository
        .findById(id)
        .map(
            cliente -> {
              cliente.setNome(novoCliente.getNome());
              cliente.setCpf(novoCliente.getCpf());
              cliente.setDataDeNascimento(novoCliente.getDataDeNascimento());
              cliente.setEmail(novoCliente.getEmail());
              cliente.setSobrenome(novoCliente.getSobrenome());
              return clienteRepository.save(cliente);
            })
        .orElseGet(
            () -> {
              novoCliente.setId(id);
              return clienteRepository.save(novoCliente);
            });
  }

  @DeleteMapping("/clientes/{id}")
  void deleteCliente(@PathVariable Long id) {
    clienteRepository.deleteById(id);
  }
}
