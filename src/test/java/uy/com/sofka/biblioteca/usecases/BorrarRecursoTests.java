package uy.com.sofka.biblioteca.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BorrarRecursoTests {
  
  @MockBean
  private IRecursoRepository repository;

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
  
  @Test
  @DisplayName("Test deleteById Error - Id not valid")
  public void borrarRecursoNotValid(){
    when(repository.deleteById("")).thenReturn(Mono.error(new IllegalArgumentException("El Id provisto no es valido")));

    borrarUseCase.apply("")
                 .subscribe(
                    null,
                    error->{
                      assertEquals("El Id provisto no es valido", error.getMessage());
                    });
  }
}
