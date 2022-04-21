package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.DevolverRecurso;

@Service
@Validated
public class UseCaseDevolver implements DevolverRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply(String id) {
    return repository.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .switchIfEmpty(Mono.error(new IllegalArgumentException("No existe el recurso")))
                      .flatMap(recurso -> {
                        if(!recurso.isDisponible()) {
                          recurso.setDisponible(true);
                          recurso.setFecha_prestamo(null);
                          return repository.save(recurso);
                        }
                        else
                          throw new IllegalStateException("El recurso no está prestado, por lo que no se puede devolver");
                      })
                      .onErrorMap(e -> e)
                      .then();
  }
  
}

