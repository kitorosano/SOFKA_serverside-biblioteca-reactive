package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteRecurso {
  Mono<Void> apply(String id);
}
