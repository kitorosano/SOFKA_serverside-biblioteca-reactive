package uy.com.sofka.biblioteca.mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;

@Component
public class RecursoMapper {
  
  //  ======= DTO -> ENTITY ======== //
  public Function<RecursoDTO, Recurso> mapDTO2Entity() {
    return dto -> {
      var recurso = new Recurso();
      recurso.setId(dto.getId());    
      recurso.setNombre(dto.getNombre());
      recurso.setTipo(dto.getTipo());
      recurso.setTema(dto.getTema());
      recurso.setDisponible(dto.isDisponible());
      recurso.setFecha_prestamo(dto.getFecha_prestamo());
      return recurso;
    };
  }

  public Function<RecursoDTO, Recurso> mapDTO2EntityUpdate(Recurso recurso) {
    return dto -> {
      var entity = recurso;
      if(dto.getId() != null) entity.setId(dto.getId());
      if(dto.getNombre() != null) entity.setNombre(dto.getNombre());
      if(dto.getTipo() != null) entity.setTipo(dto.getTipo());
      if(dto.getTema() != null) entity.setTema(dto.getTema());
      if(dto.isDisponible() != null) entity.setDisponible(dto.isDisponible());
      if(dto.getFecha_prestamo() != null) entity.setFecha_prestamo(dto.getFecha_prestamo());
      return recurso;
    };
  }
  
  //  ======= ENTITY -> DTO ======== //
  public Function<Recurso, RecursoDTO> mapEntity2DTO() {
    return entity -> {
      var recursoDTO = new RecursoDTO();
      recursoDTO.setId(entity.getId());    
      recursoDTO.setNombre(entity.getNombre());
      recursoDTO.setTipo(entity.getTipo());
      recursoDTO.setTema(entity.getTema());
      recursoDTO.setDisponible(entity.isDisponible());
      recursoDTO.setFecha_prestamo(entity.getFecha_prestamo());
      return recursoDTO;
    };
  }
}
