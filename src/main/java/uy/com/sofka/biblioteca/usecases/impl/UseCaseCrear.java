package uy.com.sofka.biblioteca.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.mappers.RecursoMapper;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.GuardarRecurso;

@Service
@Validated
public class UseCaseCrear implements GuardarRecurso {

  @Autowired
  private IRecursoRepository repository;

  private RecursoMapper mapper = new RecursoMapper();

  @Override
  public Mono<RecursoDTO> apply(RecursoDTO recursoDTO) {
    // validar que el nombre, tipo y tema del recursoDTO no sean nulos o vacios
    if(recursoDTO.getNombre() == null || recursoDTO.getNombre().isEmpty())
      return Mono.error(new Exception("El nombre del recurso no puede ser nulo o vacio"));
    if(recursoDTO.getTipo() == null || recursoDTO.getTipo().isEmpty())
      return Mono.error(new Exception("El tipo del recurso no puede ser nulo o vacio"));
    if(recursoDTO.getTema() == null || recursoDTO.getTema().isEmpty())
      return Mono.error(new Exception("El tema del recurso no puede ser nulo o vacio"));
    
    Recurso recurso = mapper.mapDTO2Entity().apply(recursoDTO);
    return repository.save(recurso).map(mapper.mapEntity2DTO());
  }
  
}
