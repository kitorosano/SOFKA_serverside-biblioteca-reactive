package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import uy.com.sofka.biblioteca.usecases.impl.UseCaseBorrarTodos;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BorrarRecursosRouter {
  
  @Bean
  public RouterFunction<ServerResponse> purgeAll(UseCaseBorrarTodos useCaseBorrarTodos) {
    return route(
            DELETE("/recursos").and(accept(MediaType.APPLICATION_JSON)),
            request -> useCaseBorrarTodos.apply()
                                          .then(ServerResponse.ok()
                                                              .contentType(MediaType.TEXT_PLAIN)
                                                              .bodyValue("Recursos borrados")
                                          )
    );
  }
  
}
