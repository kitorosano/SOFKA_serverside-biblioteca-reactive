package uy.com.sofka.biblioteca.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class PrestarRecursoTests {
  
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private PrestarRecurso prestarUseCase;
  
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

    var mono = Mono.just(recurso);
    when(repository.findById("1111")).thenReturn(mono);
    when(repository.save(any())).thenReturn(mono);
    
    prestarUseCase.apply("1111")
                  .then(
                    mono.flatMap(recurso1 -> {
                      assertEquals("1111", recurso1.getId());
                      assertEquals("Debajo del sol", recurso1.getNombre());
                      assertEquals("Libro", recurso1.getTipo());
                      assertEquals("Fantasia", recurso1.getTema());
                      assertEquals(false, recurso1.isDisponible());
                      assertEquals(LocalDate.now(), recurso1.getFecha_prestamo());
                      return Mono.just(recurso1);
                    })
                  );
  }

  
  @Test
  @DisplayName("Test prestar Error - Recurso no disponible")
  public void prestarRecursoNoDisponible(){
    var recurso = new Recurso();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    var mono = Mono.just(recurso);
    when(repository.findById("1111")).thenReturn(mono);
    
    prestarUseCase.apply("1111")
                  .subscribe(
                    null, 
                    error -> {
                      assertEquals("El recurso ya está prestado, es necesario devolverlo primero", error.getMessage());
                    }
                  );
  }

  
  @Test
  @DisplayName("Test update Error - Not found")
  public void prestarRecursoErrorNotFound() {

    when(repository.findById("2222")).thenReturn(Mono.empty());

    prestarUseCase.apply("2222")
                    .subscribe(
                        null, 
                        error -> {
                          assertEquals("No existe el recurso", error.getMessage());
                        });
  }
}
