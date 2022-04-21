package uy.com.sofka.biblioteca.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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
public class DevolverRecursoTests {
  
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private DevolverRecurso devolverUseCase;
  
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

    var mono = Mono.just(recurso);
    when(repository.findById("1111")).thenReturn(mono);
    when(repository.save(any())).thenReturn(mono);
    
    devolverUseCase.apply("1111")
                 .subscribe(e->
                      mono.flatMap(recurso1 -> {
                        assertEquals("1111", recurso1.getId());
                        assertEquals("Debajo del sol", recurso1.getNombre());
                        assertEquals("Libro", recurso1.getTipo());
                        assertEquals("Fantasia", recurso1.getTema());
                        assertEquals(true, recurso1.isDisponible());
                        assertNull(recurso1.getFecha_prestamo());
                        return Mono.just(recurso1);
                      })
                    ); 
  }

  
  @Test
  @DisplayName("Test devolver Error - Recurso disponible")
  public void devolverRecursoDisponible(){
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(true);
    recurso.setFecha_prestamo(null);

    var mono = Mono.just(recurso);
    when(repository.findById("1111")).thenReturn(mono);
    
    devolverUseCase.apply("1111")
                  .subscribe(
                    null, 
                    error -> {
                      assertEquals("El recurso no esta prestado, por lo que no se puede devolver", error.getMessage());
                    }
                  );
  }

  
  @Test
  @DisplayName("Test update Error - Not found")
  public void devolverRecursoErrorNotFound() {

    when(repository.findById("2222")).thenReturn(Mono.empty());

    devolverUseCase.apply("2222")
                    .subscribe(
                        null, 
                        error -> {
                          assertEquals("No existe el recurso", error.getMessage());
                        });
  }
}
