package uy.com.sofka.biblioteca.repositories;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.models.Recurso;

public interface IRecursoRepository extends ReactiveMongoRepository<Recurso, String> {
  Flux<Recurso> findByTipoContaining(String tipo);
  Flux<Recurso> findByTemaContaining(String tema);
}