package uy.com.sofka.biblioteca.mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;

public class RecursoMapper {
  
  //  ======= DTO -> ENTITY ======== //
  public Recurso fromDTO2Entity(RecursoDTO dto){
    Recurso recurso = new Recurso();
    recurso.setId(dto.getId());    
    recurso.setNombre(dto.getNombre());
    recurso.setTipo(dto.getTipo());
    recurso.setTema(dto.getTema());
    recurso.setDisponible(dto.isDisponible());
    recurso.setFecha_prestamo(dto.getFecha_prestamo());
    return recurso;
  }

  public Recurso fromDTO2Entity(RecursoDTO dto, Recurso recurso){
    recurso.setId(dto.getId());    
    recurso.setNombre(dto.getNombre());
    recurso.setTipo(dto.getTipo());
    recurso.setTema(dto.getTema());
    recurso.setDisponible(dto.isDisponible());
    recurso.setFecha_prestamo(dto.getFecha_prestamo());
    return recurso;
  }

  //  ======= ENTITY -> DTO ======== //
  public RecursoDTO fromEntity2DTO(Recurso entity){
    RecursoDTO recursoDTO = new RecursoDTO();
    recursoDTO.setId(entity.getId());
    recursoDTO.setNombre(entity.getNombre());
    recursoDTO.setTipo(entity.getTipo());
    recursoDTO.setTema(entity.getTema());
    recursoDTO.setDisponible(entity.isDisponible());
    recursoDTO.setFecha_prestamo(entity.getFecha_prestamo());
    return recursoDTO;
  }

  
  public List<RecursoDTO> fromListEntity2ListDTO(List<Recurso> entity) { 
    if (entity == null) return null;
    
    List<RecursoDTO> dtoList = new ArrayList<>(entity.size());
    Iterator<Recurso> iterator = entity.iterator();

    while(iterator.hasNext()) {
      Recurso recurso = (Recurso)iterator.next();
      dtoList.add(fromEntity2DTO(recurso));
    }

    return dtoList;
  }
}
