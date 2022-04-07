package uy.com.sofka.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.models.Recurso;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;
import uy.com.sofka.biblioteca.usecases.*;

@SpringBootTest
public class RecursoServiceTests {

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

  @Autowired
  private BorrarRecurso borrarUseCase;
  @Test
  @DisplayName("Test deleteById Success")
  public void borrarRecurso(){
    when(repository.deleteById("1111")).thenReturn(Mono.empty());

    borrarUseCase.apply("1111")
                 .subscribe(e->
                   verify(repository, times(1)).deleteById("1111")
                 );
  }

  @Autowired
  private BorrarTodoRecursos borrarTodoUseCase;
  @Test
  @DisplayName("Test deleteAll Success")
  public void borrarTodos(){
    when(repository.deleteAll()).thenReturn(Mono.empty());

    borrarTodoUseCase.apply()
                      .subscribe(e->
                        verify(repository, times(1)).deleteAll()
                      );
  }
}
