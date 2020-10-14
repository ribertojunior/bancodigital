package com.example.bancodigital.controller.cliente;

import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class ClienteController {
  private final ClienteRepository repository;

  private final ClienteModelAssembler assembler;

  @GetMapping("/clientes")
  CollectionModel<EntityModel<Cliente>> all() {
    List<EntityModel<Cliente>> clientes =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    return CollectionModel.of(clientes,
        linkTo(methodOn(ClienteController.class).all()).withSelfRel());
  }

  @PostMapping("/clientes")
  ResponseEntity<?> newCliente(@RequestBody Cliente cliente) {
    EntityModel<Cliente> entityModel = assembler.toModel(repository.save(cliente));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @GetMapping("/clientes/{id}")
  EntityModel<Cliente> one(@PathVariable Long id) {
    Cliente cliente =
        repository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    return assembler.toModel(cliente);
  }

  @PutMapping("/clientes/{id}")
  ResponseEntity<?> replaceCliente(@RequestBody Cliente novoCliente, @PathVariable Long id) {
    Cliente updated = repository
        .findById(id)
        .map(
            cliente -> {
              cliente.setNome(novoCliente.getNome());
              cliente.setCpf(novoCliente.getCpf());
              cliente.setDataDeNascimento(novoCliente.getDataDeNascimento());
              cliente.setEmail(novoCliente.getEmail());
              cliente.setSobrenome(novoCliente.getSobrenome());
              return repository.save(cliente);
            })
        .orElseGet(
            () -> {
              novoCliente.setId(id);
              return repository.save(novoCliente);
            });
    EntityModel<Cliente> entityModel = assembler.toModel(updated);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @DeleteMapping("/clientes/{id}")
  ResponseEntity<?> deleteCliente(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
