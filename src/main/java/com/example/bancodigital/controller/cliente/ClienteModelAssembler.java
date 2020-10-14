package com.example.bancodigital.controller.cliente;

import com.example.bancodigital.entity.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

  @Override
  public EntityModel<Cliente> toModel(Cliente cliente) {
    return EntityModel.of(cliente, linkTo(methodOn(ClienteController.class).one(cliente.getId())).withSelfRel(),
        linkTo(methodOn(ClienteController.class).all()).withRel("clientes"));
  }
}
