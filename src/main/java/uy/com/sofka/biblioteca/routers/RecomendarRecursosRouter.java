package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

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
              // Obtener parametros del request (tipo, tema)
              var tipo = request.queryParam("tipo").orElse("");
              var tema = request.queryParam("tema").orElse("");
              
              return useCaseRecomendar.apply(tipo, tema)
                                      .collectList()
                                      .flatMap(recurso -> {
                                        if(recurso.size() == 0)
                                          return ServerResponse.noContent().build();

                                        return ServerResponse.ok()
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .bodyValue(recurso);
                                        });
            }
    );
  }
}
