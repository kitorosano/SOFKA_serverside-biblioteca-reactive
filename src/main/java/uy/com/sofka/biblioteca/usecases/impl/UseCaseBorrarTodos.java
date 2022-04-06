package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.DeleteAllRecurso;

@Service
@Validated
public class UseCaseBorrarTodos implements DeleteAllRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply() {
    return repository.deleteAll().onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"));
  }
  
}
