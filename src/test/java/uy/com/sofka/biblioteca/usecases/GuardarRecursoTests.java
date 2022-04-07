package uy.com.sofka.biblioteca.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;

@SpringBootTest
public class GuardarRecursoTests {
  
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private GuardarRecurso saveUseCase;

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

    var mono = Mono.just(recurso);

    when(repository.save(any())).thenReturn(mono);

    saveUseCase.apply(dto)
               .subscribe(
                  recurso1 -> {
                    assertEquals("2222", recurso1.getId());
                    assertEquals("Condorito", recurso1.getNombre());
                    assertEquals("Revista", recurso1.getTipo());
                    assertEquals("Comedia", recurso1.getTema());
                    assertEquals(true, recurso1.isDisponible());
                    assertNull(recurso1.getFecha_prestamo());
                  });
  }
  @Test
  @DisplayName("Test save ERROR: Nombre null/empty")
  public void crearRecursoNombreNull() {
    var dto = new RecursoDTO();
    dto.setTipo("Revista");
    dto.setTema("Comedia");

    saveUseCase.apply(dto)
               .subscribe(
                 null, 
                  error -> {
                    assertEquals("El nombre del recurso no puede ser nulo o vacio", error.getMessage());
                  });
  }
}
