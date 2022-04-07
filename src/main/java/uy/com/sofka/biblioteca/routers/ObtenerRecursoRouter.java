package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.usecases.impl.UseCaseObtener;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ObtenerRecursoRouter {
  @Bean
  public RouterFunction<ServerResponse> get(UseCaseObtener useCaseObtener) {
    return route(
            GET("/recursos/recurso/{id}").and(accept(MediaType.APPLICATION_JSON)),
            request -> {
              Mono<RecursoDTO> recurso = useCaseObtener.apply(request.pathVariable("id"));
              return recurso.hasElement().flatMap(hasElements -> 
                hasElements ? ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(BodyInserters.fromPublisher(
                                              recurso, 
                                              RecursoDTO.class
                                            )) 
                            : ServerResponse.notFound().build()
              );
            }
    );
  }
  
}
