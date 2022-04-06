package uy.com.sofka.biblioteca.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;

@Component
public class RecursoMapper {
  
  //  ======= DTO -> ENTITY ======== //
  public Function<RecursoDTO, Recurso> mapDTO2Entity() {
    return dto2Validate -> {
      var validatedDTO = mapDTO2ValidatedDTO(dto2Validate);
      var recurso = new Recurso();
      recurso.setId(validatedDTO.getId());    
      recurso.setNombre(validatedDTO.getNombre());
      recurso.setTipo(validatedDTO.getTipo());
      recurso.setTema(validatedDTO.getTema());
      recurso.setDisponible(validatedDTO.isDisponible());
      recurso.setFecha_prestamo(validatedDTO.getFecha_prestamo());
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

  // ======= DTO -> VALIDATED DTO ======== //
  public RecursoDTO mapDTO2ValidatedDTO(RecursoDTO dto) {
      var validated = new RecursoDTO();
      validated.setId(dto.getId());    
      validated.setNombre(dto.getNombre());
      validated.setTipo(dto.getTipo());
      validated.setTema(dto.getTema());
      validated.setDisponible(dto.isDisponible());
      validated.setFecha_prestamo(dto.getFecha_prestamo());
      return validated;
  }
}
