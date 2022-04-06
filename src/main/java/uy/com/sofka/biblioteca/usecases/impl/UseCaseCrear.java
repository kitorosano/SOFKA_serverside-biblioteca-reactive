package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.SaveRecurso;

@Service
@Validated
public class UseCaseCrear implements SaveRecurso {

  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Mono<RecursoDTO> apply(RecursoDTO recursoDTO) {
    Recurso recurso = mapper.mapDTO2Entity().apply(recursoDTO);
    return repository.save(recurso).map(mapper.mapEntity2DTO());
  }
  
}
