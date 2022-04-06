package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;

@FunctionalInterface
public interface FindRecomendedRecurso {
  Flux<RecursoDTO> apply(String tipo, String tema);
}
