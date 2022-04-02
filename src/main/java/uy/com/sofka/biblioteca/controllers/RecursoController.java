package uy.com.sofka.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.services.IRecursoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/recursos")
@CrossOrigin(origins = "*")
public class RecursoController {
  
  @Autowired
  IRecursoService service;

  @PostMapping("")
  public RecursoDTO crear(@RequestBody RecursoDTO recursoDTO) {
    return service.crear(recursoDTO);
  }

  @GetMapping("")
  public List<RecursoDTO> obtenerTodos() {
    return service.obtenerTodos();
  }

  @GetMapping("/{id}")
  public RecursoDTO obtenerPorId(@PathVariable("id") String id) {
    return service.obtenerPorId(id);
  }

  @GetMapping("/recomendar")
  public List<RecursoDTO> recomendarRecursos(String semejante) {
    return service.recomendarRecursos(semejante);
  }

  @PutMapping("/{id}")
  public RecursoDTO modificar(@PathVariable("id") String id, @RequestBody RecursoDTO recursoDTO) {
    return service.modificar(recursoDTO);
  }

  @PutMapping("/{id}/prestar")
  public void prestarRecurso(@PathVariable("id") String id) {
    service.prestarRecurso(id);
  }

  @PutMapping("/{id}/devolver")
  public void devolverRecurso(@PathVariable("id") String id) {
    service.devolverRecurso(id);
  }

  @DeleteMapping("/{id}")
  public void borrar(@PathVariable("id") String id) {
    service.borrar(id);
  }
}
