package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.FindRecomendedRecurso;

@Service
@Validated
public class UseCaseRecomendarRecursos implements FindRecomendedRecurso {
  
  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Flux<RecursoDTO> apply(String tipo, String tema) {
    return Flux.concat(repository.findByTipoContaining(tipo).switchIfEmpty(Flux.empty()), 
                    repository.findByTemaContaining(tema).switchIfEmpty(Flux.empty()))
                .distinct()
                .map(mapper.mapEntity2DTO());
  }
}
