package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import uy.com.sofka.biblioteca.usecases.impl.UseCaseObtener;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.Objects;

@Configuration
public class ObtenerRecursoRouter {
  @Bean
  public RouterFunction<ServerResponse> get(UseCaseObtener useCaseObtener) {
    return route(
            GET("/recursos/recurso/{id}").and(accept(MediaType.APPLICATION_JSON)),
            request -> useCaseObtener.apply(request.pathVariable("id"))
                                      .flatMap(recurso -> {
                                        if(Objects.isNull(recurso))
                                          return ServerResponse.notFound().build();

                                        return ServerResponse.ok()
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .bodyValue(recurso);
                                        })
                                      .onErrorResume(error -> 
                                        ServerResponse.badRequest()
                                                      .contentType(MediaType.TEXT_PLAIN)
                                                      .bodyValue("El id del recurso no es valido"))
    );
  }
  
}
