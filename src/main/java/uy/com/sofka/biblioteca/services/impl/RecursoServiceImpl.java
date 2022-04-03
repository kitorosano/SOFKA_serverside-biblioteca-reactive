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
    try {
      Recurso recurso = mapper.fromDTO2Entity(recursoDTO);
      recurso = repositorio.save(recurso);
      return mapper.fromEntity2DTO(recurso);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public List<RecursoDTO> obtenerTodos() {
    List<Recurso> recursos = (List<Recurso>) repositorio.findAll();
    return mapper.fromListEntity2ListDTO(recursos);
  }
  public RecursoDTO obtenerPorId(String id) {
    Recurso recurso = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    return mapper.fromEntity2DTO(recurso);
  }

  public List<RecursoDTO> recomendarRecursos(String tipo, String tema){
    List<Recurso> semejantes = new ArrayList<>();
    if(!tipo.isEmpty())
      semejantes.addAll((List<Recurso>) repositorio.findByTipoContaining(tipo));
    if(!tema.isEmpty())
      semejantes.addAll((List<Recurso>) repositorio.findByTemaContaining(tema));

    // remover de la lista de semejantes los duplicados
    List<Recurso> semejantesSinDuplicados = new ArrayList<>();
    for(Recurso r : semejantes){
      if(!semejantesSinDuplicados.contains(r))
        semejantesSinDuplicados.add(r);
    }
    return mapper.fromListEntity2ListDTO(semejantesSinDuplicados);
  }

  public RecursoDTO modificar(String id, RecursoDTO recursoDTO) {
    Recurso recurso = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    Recurso recursoModificado = mapper.fromDTO2EntityUpdate(recursoDTO, recurso);
    return mapper.fromEntity2DTO(repositorio.save(recursoModificado));
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
