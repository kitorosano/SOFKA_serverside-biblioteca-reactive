package uy.com.sofka.biblioteca.usecases;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface BorrarRecurso {
  Mono<Void> apply(String id);
}
