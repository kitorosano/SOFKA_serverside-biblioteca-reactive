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

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;

@SpringBootTest
public class ObtenerTodoRecursosTests {
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private ObtenerTodoRecursos findAllUseCase;
  
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

    var flux = Flux.just(recurso1, recurso2);

    when(repository.findAll()).thenReturn(flux);

    findAllUseCase.apply()
                  .collectList()
                  .subscribe(
                    recursos -> {
                      assertEquals(2, recursos.size());
                      assertEquals("1111", recursos.get(0).getId());
                      assertEquals("Debajo del sol", recursos.get(0).getNombre());
                      assertEquals("Libro", recursos.get(0).getTipo());
                      assertEquals("Fantasia", recursos.get(0).getTema());
                      assertEquals(false, recursos.get(0).isDisponible());
                      assertEquals(LocalDate.now(), recursos.get(0).getFecha_prestamo());

                      assertEquals("2222", recursos.get(1).getId());
                      assertEquals("Condorito", recursos.get(1).getNombre());
                      assertEquals("Revista", recursos.get(1).getTipo());
                      assertEquals("Comedia", recursos.get(1).getTema());
                      assertEquals(true, recursos.get(1).isDisponible());
                      assertNull(recursos.get(1).getFecha_prestamo());
                    });
  }

  
}
