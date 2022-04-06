package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.FindByIdRecurso;

@Service
@Validated
public class UseCaseObtener implements FindByIdRecurso {

  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Mono<RecursoDTO> apply(String id) {
    return repository.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .map(mapper.mapEntity2DTO());
  }
  
}
