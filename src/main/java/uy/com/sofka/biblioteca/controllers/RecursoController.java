package uy.com.sofka.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<?> crear(@RequestBody RecursoDTO recursoDTO) {
    try {
      RecursoDTO recurso = service.crear(recursoDTO);
      return new ResponseEntity<RecursoDTO>(recurso, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @GetMapping("")
  public ResponseEntity<?> obtenerTodos() {
    try {
      List<RecursoDTO> recursos = service.obtenerTodos();
      if(recursos.isEmpty())
        return new ResponseEntity<String>("No hay recursos", HttpStatus.NO_CONTENT);
      return new ResponseEntity<List<RecursoDTO>>(recursos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerPorId(@PathVariable("id") String id) {
    try {
      return new ResponseEntity<RecursoDTO>(service.obtenerPorId(id), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @GetMapping("{id}/disponible")
  public ResponseEntity<?> obtenerDisponible(@PathVariable("id") String id) {
    try {
      RecursoDTO recurso = service.obtenerPorId(id);
      if(!recurso.isDisponible())
        return new ResponseEntity<String>("El recurso no esta disponible. Se presto el dia: " + recurso.getFecha_prestamo().toString(), HttpStatus.OK);
      return new ResponseEntity<String>("El recurso esta disponible", HttpStatus.OK);
    } catch (IllegalArgumentException e ){
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @GetMapping("/recomendar")
  public ResponseEntity<?> recomendarRecursos(@RequestParam(value = "tipo", defaultValue = "") String tipo, @RequestParam(value = "tema", defaultValue = "") String tema) {
    try {
      List<RecursoDTO> recursos = service.recomendarRecursos(tipo, tema);
      if(recursos.isEmpty())
        return new ResponseEntity<String>("No hay recursos semejantes", HttpStatus.NO_CONTENT);
      return new ResponseEntity<List<RecursoDTO>>(recursos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> modificar(@PathVariable("id") String id, @RequestBody RecursoDTO recursoDTO) {
    try {
      RecursoDTO recurso = service.modificar(id, recursoDTO);
      return new ResponseEntity<RecursoDTO>(recurso, HttpStatus.OK);
    } catch (IllegalArgumentException e ){
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/{id}/prestar")
  public ResponseEntity<?> prestarRecurso(@PathVariable("id") String id) {
    try {
      service.prestarRecurso(id);
      return new ResponseEntity<String>("El recurso ha sido prestado", HttpStatus.OK);
    } catch (IllegalArgumentException e ){
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalStateException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_MODIFIED);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/{id}/devolver")
  public ResponseEntity<?> devolverRecurso(@PathVariable("id") String id) {
    try {
      service.devolverRecurso(id);
      return new ResponseEntity<String>("El recurso ha sido devuelto", HttpStatus.OK);
    } catch (IllegalArgumentException e ){
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalStateException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_MODIFIED);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> borrar(@PathVariable("id") String id) {
    try {
      service.borrar(id);
      return new ResponseEntity<String>("El recurso ha sido borrado", HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("")
  public ResponseEntity<?> borrarTodos() {
    try {
      service.borrarTodos();
      return new ResponseEntity<String>("Todos los recursos han sido borrados", HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
  }
}
