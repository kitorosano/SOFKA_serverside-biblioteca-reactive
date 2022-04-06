package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.usecases.impl.UseCaseDevolver;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DevolverRecursoRouter {
  @Bean
  public RouterFunction<ServerResponse> modify(UseCaseDevolver useCaseDevolver) {
    return route(
            PUT("/recursos/{id}/devolver").and(accept(MediaType.APPLICATION_JSON)),
            request -> request.bodyToMono(RecursoDTO.class)
                              .flatMap(recursoDTO -> 
                                useCaseDevolver.apply(request.pathVariable("id"))
                                                .flatMap(result -> 
                                                  ServerResponse.ok()
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .bodyValue(result)
                                                )
                              )
                              .onErrorResume(e -> ServerResponse.badRequest()
                                                           .contentType(MediaType.TEXT_PLAIN)
                                                           .bodyValue("ERROR: " + e.getMessage()))
    );
  }
}
