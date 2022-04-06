package uy.com.sofka.biblioteca.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;

public interface IRecursoService {
  Mono<RecursoDTO> crear(RecursoDTO recursoDTO);
  Flux<RecursoDTO> obtenerTodos();
  Mono<RecursoDTO> obtenerPorId(String id);
  Flux<RecursoDTO> recomendarRecursos(String tipo, String tema);
  Mono<RecursoDTO> modificar(String id, RecursoDTO recursoDTO);
  void prestarRecurso(String id);
  void devolverRecurso(String id);
  void borrar(String id);
  void borrarTodos();
}
