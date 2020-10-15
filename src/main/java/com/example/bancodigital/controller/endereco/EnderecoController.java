package com.example.bancodigital.controller.endereco;

import com.example.bancodigital.controller.cliente.ClienteNotFoundException;
import com.example.bancodigital.entity.Endereco;
import com.example.bancodigital.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.bancodigital.utils.Utils.validaEndereco;
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

  @PostMapping("/enderecos")
  ResponseEntity<?> newEndereco(@RequestBody Endereco endereco) {
    String ret;
    try {
      if ((ret = validaEndereco(endereco)).trim().isEmpty()) {
        if (endereco.getCliente_id() == null || endereco.getCliente_id() <= 0) {
          throw new ClienteNotFoundException(null);
        }
        EntityModel<Endereco> enderecoEntityModel = assembler.toModel(repository.save(endereco));
        return ResponseEntity.created(enderecoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(enderecoEntityModel);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create().withTitle("Erro de Endereco").withDetail(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(
            Problem.create()
                .withTitle("Cliente com informações faltantes ou inconsistentes.")
                .withDetail(ret.trim()));
  }

  @GetMapping("/enderecos/{id}")
  public EntityModel<Endereco> one(@PathVariable Long id) {
    Endereco endereco =
        repository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));
    return assembler.toModel(endereco);
  }


}
