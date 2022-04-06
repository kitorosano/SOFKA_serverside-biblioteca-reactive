package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteAllRecurso {
  Mono<Void> apply();
}