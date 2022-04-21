package uy.com.sofka.biblioteca.usecases;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecomendarRecursosTests {
  
  @MockBean
  private IRecursoRepository repository;

  @Autowired
  private RecomendarRecursos recomendarUseCase;
  
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
    recurso3.setId("3333");
    recurso3.setNombre("FFF");
    recurso3.setTipo("E-book");
    recurso3.setTema("Comedia");
    recurso3.setDisponible(true);
    recurso3.setFecha_prestamo(null);
    
    var recurso4 = new Recurso();
    recurso4.setId("4444");
    recurso4.setNombre("Model Eyes");
    recurso4.setTipo("Revista");
    recurso4.setTema("Anuncio");
    recurso4.setDisponible(true);
    recurso4.setFecha_prestamo(null);
    
    var fluxRevista = Flux.just(recurso2, recurso4);
    var fluxComedia = Flux.just(recurso2, recurso3);

    when(repository.findByTipoContaining("Revista")).thenReturn(fluxRevista);
    when(repository.findByTemaContaining("Comedia")).thenReturn(fluxComedia);

    recomendarUseCase.apply("Revista", "Comedia")
                     .collectList()
                     .subscribe(
                        recursos -> {
                          assertEquals(2, recursos.size());
                          assertEquals("2222", recursos.get(0).getId());
                          assertEquals("Condorito", recursos.get(0).getNombre());
                          assertEquals("Revista", recursos.get(0).getTipo());
                          assertEquals("Comedia", recursos.get(0).getTema());
                          assertEquals(true, recursos.get(0).isDisponible());
                          assertNull(recursos.get(0).getFecha_prestamo());
                          
                          assertEquals("4444", recursos.get(1).getId());
                          assertEquals("Model Eyes", recursos.get(1).getNombre());
                          assertEquals("Revista", recursos.get(1).getTipo());
                          assertEquals("Anuncio", recursos.get(1).getTema());
                          assertEquals(true, recursos.get(1).isDisponible());
                          assertNull(recursos.get(1).getFecha_prestamo());

                          assertEquals("3333", recursos.get(2).getId());
                          assertEquals("FFF", recursos.get(2).getNombre());
                          assertEquals("E-book", recursos.get(2).getTipo());
                          assertEquals("Comedia", recursos.get(2).getTema());
                          assertEquals(true, recursos.get(2).isDisponible());
                          assertNull(recursos.get(2).getFecha_prestamo());
                        });
  }

}
