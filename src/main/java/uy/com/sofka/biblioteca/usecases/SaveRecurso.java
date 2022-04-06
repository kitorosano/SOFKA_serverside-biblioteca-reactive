package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;

@FunctionalInterface
public interface SaveRecurso {
  public Mono<RecursoDTO> apply(RecursoDTO recursoDTO);
}
