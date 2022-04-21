package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.BorrarTodoRecursos;

@Service
@Validated
public class UseCaseBorrarTodos implements BorrarTodoRecursos {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply() {
    return repository.deleteAll();
  }
  
}
