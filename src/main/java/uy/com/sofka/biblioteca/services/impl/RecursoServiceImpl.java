package uy.com.sofka.biblioteca.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  
  public RecursoDTO crear(RecursoDTO recursoDTO) {
    Recurso recurso = mapper.fromDTO2Entity(recursoDTO);
    return mapper.fromEntity2DTO(repositorio.save(recurso));
  }

  public List<RecursoDTO> obtenerTodos() {
    List<Recurso> recursos = (List<Recurso>) repositorio.findAll();
    return mapper.fromListEntity2ListDTO(recursos);
  }
  public RecursoDTO obtenerPorId(String id) {
    Recurso recurso = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    return mapper.fromEntity2DTO(recurso);
  }

  public List<RecursoDTO> recomendarRecursos(String semejante){
    List<Recurso> semejantes = new ArrayList<>();
    semejantes.addAll((List<Recurso>) repositorio.findByTipo(semejante));
    semejantes.addAll((List<Recurso>) repositorio.findByTema(semejante));
    return mapper.fromListEntity2ListDTO(semejantes);
  }

  public RecursoDTO modificar(RecursoDTO recursoDTO) {
    Recurso recurso = mapper.fromDTO2Entity(recursoDTO);
    repositorio.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    return mapper.fromEntity2DTO(repositorio.save(recurso));
  }

  public void prestarRecurso(String id) {
    Recurso recurso = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    if(recurso.isDisponible()) {
      recurso.setDisponible(false);
      recurso.setFecha_prestamo(LocalDate.now());
      repositorio.save(recurso);
    }
    else
      throw new RuntimeException("El recurso ya está prestado");
  }

  public void devolverRecurso(String id){
    Recurso recurso = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    if(!recurso.isDisponible()) {
      recurso.setDisponible(true);
      recurso.setFecha_prestamo(null);
      repositorio.save(recurso);
    }
    else
      throw new RuntimeException("El recurso no se encuentra prestado, está disponible");
  }

  public void borrar(String id) {
    repositorio.deleteById(id);
  }

}
