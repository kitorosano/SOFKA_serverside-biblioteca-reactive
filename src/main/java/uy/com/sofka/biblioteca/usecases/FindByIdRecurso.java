package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;

@FunctionalInterface
public interface FindByIdRecurso {
  Mono<RecursoDTO> apply(String id);
}
