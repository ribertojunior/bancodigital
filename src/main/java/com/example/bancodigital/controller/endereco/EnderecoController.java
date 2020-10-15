package com.example.bancodigital.controller.endereco;

import com.example.bancodigital.entity.Endereco;
import com.example.bancodigital.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class EnderecoController {
  private final EnderecoRepository repository;
  private final EnderecoModelAssembler assembler;

  @GetMapping("/enderecos")
  public CollectionModel<EntityModel<Endereco>> all() {
    List<EntityModel<Endereco>> enderecos =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    return CollectionModel.of(enderecos,
        linkTo(methodOn(EnderecoController.class).all()).withSelfRel());
  }

  @GetMapping("/enderecos/{id}")
  EntityModel<Endereco> one(@PathVariable Long id) {
    Endereco endereco =
        repository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));
    return assembler.toModel(endereco);
  }


}
