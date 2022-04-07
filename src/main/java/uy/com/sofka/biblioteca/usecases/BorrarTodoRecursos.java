package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface BorrarTodoRecursos {
  Mono<Void> apply();
}