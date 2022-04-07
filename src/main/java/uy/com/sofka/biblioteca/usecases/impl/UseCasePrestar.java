package uy.com.sofka.biblioteca.usecases.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.UpdatePrestarRecurso;

@Service
@Validated
public class UseCasePrestar implements UpdatePrestarRecurso {

  @Autowired
  private IRecursoRepository repository;

  @Override
  public Mono<Void> apply(String id) {
    return repository.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .flatMap(recurso -> {
                        if(recurso.isDisponible()) {
                          recurso.setDisponible(false);
                          recurso.setFecha_prestamo(LocalDate.now());
                          return repository.save(recurso);
                        }
                        else
                          throw new IllegalStateException("El recurso ya está prestado, es necesario devolverlo primero");
                      })
                      .onErrorMap(e -> e)
                      .then();
  }
  
}
