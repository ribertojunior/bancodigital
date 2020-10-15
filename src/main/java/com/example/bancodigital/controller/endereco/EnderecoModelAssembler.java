package com.example.bancodigital.controller.endereco;

import com.example.bancodigital.entity.Endereco;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnderecoModelAssembler
    implements RepresentationModelAssembler<Endereco, EntityModel<Endereco>> {
  @Override
  public EntityModel<Endereco> toModel(Endereco endereco) {
    return EntityModel.of(
        endereco,
        linkTo(methodOn(EnderecoController.class).one(endereco.getId())).withSelfRel(),
        linkTo(methodOn(EnderecoController.class).all()).withRel("enderecos"));
  }
}
