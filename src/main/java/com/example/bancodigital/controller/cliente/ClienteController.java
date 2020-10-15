package com.example.bancodigital.controller.cliente;

import com.example.bancodigital.controller.endereco.EnderecoController;
import com.example.bancodigital.controller.endereco.EnderecoModelAssembler;
import com.example.bancodigital.entity.Cliente;
import com.example.bancodigital.entity.Endereco;
import com.example.bancodigital.repository.ClienteRepository;
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

import static com.example.bancodigital.utils.Utils.valida;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class ClienteController {
  private final ClienteRepository repository;
  private final ClienteModelAssembler assembler;

  private final EnderecoRepository enderecoRepository;
  private final EnderecoModelAssembler enderecoAssembler;

  @GetMapping("/clientes")
  CollectionModel<EntityModel<Cliente>> all() {
    List<EntityModel<Cliente>> clientes =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    return CollectionModel.of(
        clientes, linkTo(methodOn(ClienteController.class).all()).withSelfRel());
  }

  @PostMapping("/clientes")
  ResponseEntity<?> newCliente(@RequestBody Cliente cliente) {
    String ret;
    try {
      if ((ret = valida(cliente)).trim().isEmpty()) {
        if (repository.findByCpf(cliente.getCpf()) != null
            || repository.findByCpf(cliente.getCpf().replace(".", "").replace("-", "")) != null) {
          throw new ClienteRepetidoException("CPF " + cliente.getCpf() + " repetido.");
        }
        if (repository.findByEmail(cliente.getEmail()) != null) {
          throw new ClienteRepetidoException("Email " + cliente.getEmail() + " repetido.");
        }
        Endereco endereco =
            cliente.getEndereco() != null
                ? enderecoRepository.save(cliente.getEndereco())
                : enderecoRepository.save(new Endereco());
        Cliente clienteSave = repository.save(cliente);
        endereco.setCliente_id(clienteSave.getId());
        enderecoRepository.save(endereco);
        EntityModel<Cliente> entityModel = assembler.toModel(clienteSave);
        return ResponseEntity.created(
                linkTo(methodOn(EnderecoController.class).one(endereco.getId())).withSelfRel().toUri())
            .body(entityModel);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create().withTitle("Erro de Cliente").withDetail(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(
            Problem.create()
                .withTitle("Cliente com informações faltantes ou inconsistentes.")
                .withDetail(ret.trim()));
  }

  @GetMapping("/clientes/{id}")
  EntityModel<Cliente> one(@PathVariable Long id) {
    Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    return assembler.toModel(cliente);
  }

  @PutMapping("/clientes/{id}")
  ResponseEntity<?> replaceCliente(@RequestBody Cliente novoCliente, @PathVariable Long id) {
    Cliente updated =
        repository
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
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/clientes/{id}")
  ResponseEntity<?> deleteCliente(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
