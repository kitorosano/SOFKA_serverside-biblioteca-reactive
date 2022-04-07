package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.usecases.impl.UseCaseObtener;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ObtenerDisponibleRecursoRouter {
  @Bean
  public RouterFunction<ServerResponse> getDisponible(UseCaseObtener useCaseObtener) {
    return route(
            GET("/recursos/recurso/{id}/disponible").and(accept(MediaType.APPLICATION_JSON)),
            request -> {
              Mono<RecursoDTO> recurso = useCaseObtener.apply(request.pathVariable("id"));
              return recurso.hasElement().flatMap(hasElements -> 
                hasElements ? recurso.flatMap(_recurso -> _recurso.isDisponible() 
                                  ? ServerResponse.ok()
                                                  .contentType(MediaType.TEXT_PLAIN)
                                                  .bodyValue("El recurso está disponible.")
                                  : ServerResponse.ok()
                                                  .contentType(MediaType.TEXT_PLAIN)
                                                  .bodyValue("El recurso no esta disponible. Se presto el dia: " + _recurso.getFecha_prestamo().toString()) 
                              )
                            : ServerResponse.noContent().build()
              );
    });
  }
}
