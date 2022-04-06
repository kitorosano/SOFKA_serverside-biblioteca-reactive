package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdatePrestarRecurso {
  Mono<Void> apply(String id);
}
