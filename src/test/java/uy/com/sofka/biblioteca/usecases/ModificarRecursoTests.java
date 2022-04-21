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
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;



@SpringBootTest
public class ModificarRecursoTests {
  
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private ModificarRecurso modificarUseCase;

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

    var mono = Mono.just(recurso);
    when(repository.findById("1111")).thenReturn(mono);
    when(repository.save(any())).thenReturn(mono);

    modificarUseCase.apply("1111", dto)
                    .subscribe(
                        recurso1 -> {
                          assertEquals("1111", recurso1.getId());
                          assertEquals("Debajo de la luna", recurso1.getNombre());
                          assertEquals("Libro", recurso1.getTipo());
                          assertEquals("Lush", recurso1.getTema());
                          assertEquals(false, recurso1.isDisponible());
                          assertEquals(LocalDate.now(), recurso1.getFecha_prestamo());
                        });
  }

  
  @Test
  @DisplayName("Test update Error: Not found")
  public void modificarRecursoErrorNotFound() {

    var dto = new RecursoDTO();
    dto.setNombre("Debajo de la luna");
    dto.setTipo("Libro");
    dto.setTema("Lush");

    when(repository.findById("2222")).thenReturn(Mono.empty());

    modificarUseCase.apply("2222", dto)
                    .subscribe(
                        null, 
                        error -> {
                          assertEquals("No existe el recurso", error.getMessage());
                        });
  }
}
