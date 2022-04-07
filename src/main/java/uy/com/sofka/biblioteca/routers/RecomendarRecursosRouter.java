package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.usecases.impl.UseCaseRecomendar;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RecomendarRecursosRouter {
  
  @Bean
  public RouterFunction<ServerResponse> getRecomended(UseCaseRecomendar useCaseRecomendar) {
    return route(
            GET("/recursos/recomendar").and(accept(MediaType.APPLICATION_JSON)),
            request -> {
              Flux<RecursoDTO> recursos = useCaseRecomendar.apply(request.queryParam("tipo").orElse(""), request.queryParam("tema").orElse(""));
              return recursos.hasElements().flatMap(hasElements -> 
                hasElements ? ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(BodyInserters.fromPublisher(
                                              recursos, 
                                              RecursoDTO.class
                                            )) 
                            : ServerResponse.noContent().build()
              );
            }
    );
  }
}
