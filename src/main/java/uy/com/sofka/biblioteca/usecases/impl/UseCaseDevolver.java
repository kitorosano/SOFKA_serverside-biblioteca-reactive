package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.UpdateDevolverRecurso;

@Service
@Validated
public class UseCaseDevolver implements UpdateDevolverRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply(String id) {
    return repository.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .flatMap(recurso -> {
                        if(recurso.isDisponible()) {
                          recurso.setDisponible(true);
                          recurso.setFecha_prestamo(null);
                          return repository.save(recurso);
                        }
                        else
                          throw new IllegalStateException("El recurso ya está prestado");
                      })
                      .onErrorMap(e -> e)
                      .then();
  }
  
}

