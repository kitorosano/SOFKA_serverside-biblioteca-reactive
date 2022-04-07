package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.BorrarRecurso;

@Service
@Validated
public class UseCaseBorrar implements BorrarRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply(String id) {
    // Validar que el id no sea nulo o vacio
    if(id == null || id.isEmpty())
      return Mono.error(new Exception("El id del recurso no puede ser nulo o vacio"));
    return repository.deleteById(id).onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"));
  }
  
}
