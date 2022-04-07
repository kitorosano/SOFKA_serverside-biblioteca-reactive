package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface PrestarRecurso {
  Mono<Void> apply(String id);
}
