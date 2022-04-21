package uy.com.sofka.biblioteca.usecases;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.repositories.IRecursoRepository;

@SpringBootTest
public class BorrarRecursosTests {
  
  @MockBean
  private IRecursoRepository repository;

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
