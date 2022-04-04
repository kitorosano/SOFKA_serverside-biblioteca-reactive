package uy.com.sofka.biblioteca.services.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.services.IRecursoService;

@Service
public class RecursoServiceImpl implements IRecursoService {
  @Autowired
  IRecursoRepository repositorio;

  RecursoMapper mapper = new RecursoMapper();
  
  public Mono<RecursoDTO> crear(RecursoDTO recursoDTO) {
    Recurso recurso = mapper.mapDTO2Entity().apply(recursoDTO);
    return repositorio.save(recurso).map(mapper.mapEntity2DTO());
  }

  public Flux<RecursoDTO> obtenerTodos() {
    return repositorio.findAll().map(mapper.mapEntity2DTO());
  }

  public Mono<RecursoDTO> obtenerPorId(String id) {
    return repositorio.findById(id)
                      .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                      .map(mapper.mapEntity2DTO());
  }

  public Flux<RecursoDTO> recomendarRecursos(String tipo, String tema){
    Flux<Recurso> semejantesTipo = repositorio.findByTipoContaining(tipo).switchIfEmpty(Flux.empty());
    Flux<Recurso> semejantesTema = repositorio.findByTemaContaining(tema).switchIfEmpty(Flux.empty());

    Flux<Recurso> semejantes = semejantesTipo.concatWith(other -> semejantesTema.filter(other::equals));
    
    return semejantes.map(mapper.mapEntity2DTO());
  }

  public Mono<RecursoDTO> modificar(String id, RecursoDTO recursoDTO) {
    return repositorio.findById(id)
                .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                .flatMap(recurso -> {
                  return repositorio.save(mapper.mapDTO2EntityUpdate(recurso).apply(recursoDTO)) 
                                    .map(mapper.mapEntity2DTO());                 
                });
  }

  public void prestarRecurso(String id) {
    repositorio.findById(id)
                .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                .flatMap(recurso -> {
                  if(recurso.isDisponible()) {
                    recurso.setDisponible(false);
                    recurso.setFecha_prestamo(LocalDate.now());
                    return repositorio.save(recurso);
                  }
                  else
                    throw new IllegalStateException("El recurso ya está prestado");
                })
                .onErrorMap(e -> e);
    
  }

  public void devolverRecurso(String id){
    repositorio.findById(id)
                .onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"))
                .flatMap(recurso -> {
                  if(recurso.isDisponible()) {
                    recurso.setDisponible(true);
                    recurso.setFecha_prestamo(null);
                    return repositorio.save(recurso);
                  }
                  else
                    throw new IllegalStateException("El recurso ya está prestado");
                })
                .onErrorMap(e -> e);
    }

  public void borrar(String id) {
    repositorio.deleteById(id).onErrorMap(e -> new IllegalArgumentException("El Id provisto no es valido"));
  }

  public void borrarTodos() {
    repositorio.deleteAll();
  }

}
