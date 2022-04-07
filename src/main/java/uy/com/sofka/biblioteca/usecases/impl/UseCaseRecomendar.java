package uy.com.sofka.biblioteca.usecases.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UseCaseRecomendar implements FindRecomendedRecurso {
  Logger logger = LoggerFactory.getLogger(UseCaseRecomendar.class); 
  
  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Flux<RecursoDTO> apply(String tipo, String tema) {
    return Flux.merge(tipo.length() != 0 ? repository.findByTipoContaining(tipo) : Flux.empty(), 
                       tema.length() != 0 ? repository.findByTemaContaining(tema) : Flux.empty())
                .distinct()
                .map(mapper.mapEntity2DTO());
  }
}
