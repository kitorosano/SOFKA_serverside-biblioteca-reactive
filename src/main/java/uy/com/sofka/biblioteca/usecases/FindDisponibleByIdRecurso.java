package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface FindDisponibleByIdRecurso {
  Mono<Boolean> apply(String id);
}
