package uy.com.sofka.biblioteca.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;


@SpringBootTest
public class ObtenerRecursoTests {

  @MockBean
  private IRecursoRepository repository;
  
  @Autowired
  private ObtenerRecurso findByIdUseCase;
  
  
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
    
    var mono = Mono.just(recurso);

    when(repository.findById("1111")).thenReturn(mono);

    findByIdUseCase.apply("1111")
                   .subscribe(
                      recurso1 -> {
                        assertEquals("1111", recurso1.getId());
                        assertEquals("Debajo del sol", recurso1.getNombre());
                        assertEquals("Libro", recurso1.getTipo());
                        assertEquals("Fantasia", recurso1.getTema());
                        assertEquals(false, recurso1.isDisponible());
                        assertEquals(LocalDate.now(), recurso1.getFecha_prestamo());
                      });
  }

  @Test
  @DisplayName("Test findById Not found")
  public void obtenerRecursoPorIdNotFound() {

    when(repository.findById("2222")).thenReturn(Mono.empty());

    findByIdUseCase.apply("2222")
                   .subscribe(
                      recurso1 -> {
                        assertNull(recurso1);
                      });
  }
  @Test
  @DisplayName("Test findById Error: invalid ID")
  public void obtenerRecursoPorIdInvalidId() {
    String nothing = null;
    when(repository.findById(nothing)).thenReturn(Mono.error(new IllegalArgumentException("El Id provisto no es valido")));

    findByIdUseCase.apply(null)
                   .subscribe(
                      null,
                      error -> {
                        assertEquals("El Id provisto no es valido", error.getMessage());
                      });
  }
}
