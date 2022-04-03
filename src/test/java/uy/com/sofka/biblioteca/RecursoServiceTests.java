package uy.com.sofka.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.services.IRecursoService;

@SpringBootTest
public class RecursoServiceTests {

  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private IRecursoService service;

  @Test
  @DisplayName("Test findAll Success")
  public void obtenerTodosLosRecursos() {
    var recurso1 = new Recurso();
    recurso1.setId("1111");
    recurso1.setNombre("Debajo del sol");
    recurso1.setTipo("Libro");
    recurso1.setTema("Fantasia");
    recurso1.setDisponible(false);
    recurso1.setFecha_prestamo(LocalDate.now());

    var recurso2 = new Recurso();
    recurso2.setId("2222");
    recurso2.setNombre("Condorito");
    recurso2.setTipo("Revista");
    recurso2.setTema("Comedia");
    recurso2.setDisponible(true);
    recurso2.setFecha_prestamo(null);


    var lista = List.of(recurso1, recurso2);

    when(repository.findAll()).thenReturn(lista);

    var resultado = service.obtenerTodos();

    assertEquals(2, resultado.size());
    assertEquals(recurso1.getNombre(), resultado.get(0).getNombre());
    assertEquals(recurso2.getNombre(), resultado.get(1).getNombre());
  }

  @Test
  @DisplayName("Test findById Success")
  public void obtenerRecursoPorId() {
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.now());
    

    when(repository.findById("1111")).thenReturn(Optional.of(recurso));

    var resultado = service.obtenerPorId("1111");

    assertEquals(recurso.getId(), resultado.getId());
    assertEquals(recurso.getNombre(), resultado.getNombre());
    assertEquals(recurso.getTipo(), resultado.getTipo());
    assertEquals(recurso.getTema(), resultado.getTema());
    assertEquals(recurso.isDisponible(), resultado.isDisponible());
    assertEquals(recurso.getFecha_prestamo(), resultado.getFecha_prestamo());
  }

  @Test
  @DisplayName("Test save Success")
  public void crearRecurso() {
    var recurso = new Recurso();
    recurso.setId("2222");
    recurso.setNombre("Condorito");
    recurso.setTipo("Revista");
    recurso.setTema("Comedia");
    recurso.setDisponible(true);
    recurso.setFecha_prestamo(null);

    var dto = new RecursoDTO();
    dto.setNombre("Condorito");
    dto.setTipo("Revista");
    dto.setTema("Comedia");

    when(repository.save(any())).thenReturn(recurso);

    var resultado = service.crear(dto);

    assertNotNull(resultado, "el valor guardado no debe ser nulo");

    assertEquals(recurso.getNombre(), resultado.getNombre(), "el nombre no corresponde");
    assertEquals(recurso.getTipo(), resultado.getTipo(), "el tipo no corresponde");
    assertEquals(recurso.getTema(), resultado.getTema(), "el tema no corresponde");
    assertEquals(recurso.isDisponible(), resultado.isDisponible(), "la disponibilidad no corresponde");
    assertEquals(recurso.getFecha_prestamo(), resultado.getFecha_prestamo(), "la fecha no corresponde");
  }

  
  @Test
  @DisplayName("Test recomendar Success")
  public void recomendarRecursos() {
    var recurso1 = new Recurso();
    recurso1.setId("1111");
    recurso1.setNombre("Debajo del sol");
    recurso1.setTipo("Libro");
    recurso1.setTema("Fantasia");
    recurso1.setDisponible(false);
    recurso1.setFecha_prestamo(LocalDate.now());

    var recurso2 = new Recurso();
    recurso2.setId("2222");
    recurso2.setNombre("Condorito");
    recurso2.setTipo("Revista");
    recurso2.setTema("Comedia");
    recurso2.setDisponible(true);
    recurso2.setFecha_prestamo(null);
    
    var recurso3 = new Recurso();
    recurso3.setId("2222");
    recurso3.setNombre("FFF");
    recurso3.setTipo("E-book");
    recurso3.setTema("Comedia");
    recurso3.setDisponible(true);
    recurso3.setFecha_prestamo(null);
    
    var recurso4 = new Recurso();
    recurso4.setId("2222");
    recurso4.setNombre("Model Eyes");
    recurso4.setTipo("Revista");
    recurso4.setTema("Anuncio");
    recurso4.setDisponible(true);
    recurso4.setFecha_prestamo(null);
    
    var listaRevista = List.of(recurso2, recurso4);
    var listaComedia = List.of(recurso2, recurso3);

    when(repository.findByTipoContaining("Revista")).thenReturn(listaRevista);
    when(repository.findByTemaContaining("Comedia")).thenReturn(listaComedia);

    var resultado = service.recomendarRecursos("Revista", "Comedia");

    assertEquals(3, resultado.size());
    assertEquals(recurso2.getTipo(), resultado.get(0).getTipo());
    assertEquals(recurso2.getTipo(), resultado.get(1).getTipo());
    assertEquals(recurso2.getTema(), resultado.get(2).getTema());
  }

  @Test
  @DisplayName("Test update Success")
  public void modificarRecurso() {
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.now());

    var dto = new RecursoDTO();
    dto.setNombre("Debajo de la luna");
    dto.setTipo("Libro");
    dto.setTema("Lush");

    when(repository.findById("1111")).thenReturn(Optional.of(recurso));
    when(repository.save(any())).thenReturn(recurso);

    var resultado = service.modificar("1111", dto);

    assertNotNull(resultado, "el valor guardado no debe ser nulo");
    assertEquals(dto.getNombre(), resultado.getNombre(), "el nombre no corresponde");
    assertEquals(dto.getTipo(), resultado.getTipo(), "el tipo no corresponde");
    assertEquals(dto.getTema(), resultado.getTema(), "el tema no corresponde");
  }

  @Test
  @DisplayName("Test prestar Success")
  public void prestarRecurso(){
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(true);
    recurso.setFecha_prestamo(null);

    when(repository.findById("1111")).thenReturn(Optional.of(recurso));
    when(repository.save(any())).thenReturn(recurso);
    
    service.prestarRecurso("1111");

    assertEquals(false, recurso.isDisponible(), "el recurso no debe estar disponible");
    assertNotNull(recurso.getFecha_prestamo(), "la fecha de prestamo no debe ser nula"); 
  }

  @Test
  @DisplayName("Test devolver Success")
  public void devolverRecurso(){
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    when(repository.findById("1111")).thenReturn(Optional.of(recurso));
    when(repository.save(any())).thenReturn(recurso);
    
    service.devolverRecurso("1111");

    assertEquals(true, recurso.isDisponible(), "el recurso debe estar disponible");
    assertNull(recurso.getFecha_prestamo(), "la fecha de prestamo debe ser nula"); 
  }

  @Test
  @DisplayName("Test delete Success")
  public void borrarRecurso(){
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    when(repository.findById("1111")).thenReturn(Optional.of(recurso));
    service.borrar("1111");
    
    verify(repository, times(1)).deleteById(recurso.getId());
  }
}
