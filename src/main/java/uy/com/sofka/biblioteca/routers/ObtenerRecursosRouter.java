package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import uy.com.sofka.biblioteca.usecases.impl.UseCaseObtenerTodos;


import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class ObtenerRecursosRouter {
  @Bean
  public RouterFunction<ServerResponse> getAll(UseCaseObtenerTodos useCaseObtenerTodos) {
    return route(
            GET("/recursos").and(accept(MediaType.APPLICATION_JSON)),
            request -> useCaseObtenerTodos.apply()
                                          .collectList()
                                          .flatMap(recurso -> {
                                            if(recurso.size() == 0)
                                              return ServerResponse.noContent().build();

                                            return ServerResponse.ok()
                                                                  .contentType(MediaType.APPLICATION_JSON)
                                                                  .bodyValue(recurso);
                                          })
    );
  }
}