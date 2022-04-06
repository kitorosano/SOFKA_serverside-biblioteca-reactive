package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.UpdateRecurso;

@Service
@Validated
public class UseCaseModificar implements UpdateRecurso {

  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Mono<RecursoDTO> apply(String id, RecursoDTO recursoDTO) {
    return repository.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .map(found -> mapper.mapDTO2EntityUpdate(found).apply(recursoDTO))
                      .flatMap(repository::save)
                      .map(mapper.mapEntity2DTO());
  }
  
}
