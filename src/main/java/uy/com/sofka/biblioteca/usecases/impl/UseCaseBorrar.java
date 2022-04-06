package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.DeleteRecurso;

public class UseCaseBorrar implements DeleteRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply(String id) {
    return repository.deleteById(id).onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"));
  }
  
}
