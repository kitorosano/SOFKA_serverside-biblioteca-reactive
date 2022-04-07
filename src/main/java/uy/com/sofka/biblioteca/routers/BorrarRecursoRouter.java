package uy.com.sofka.biblioteca.routers;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import uy.com.sofka.biblioteca.usecases.impl.UseCaseBorrar;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BorrarRecursoRouter {
  
  @Bean
  public RouterFunction<ServerResponse> purge(UseCaseBorrar useCaseBorrar) {
    return route(
            DELETE("/recursos/recurso/{id}").and(accept(MediaType.APPLICATION_JSON)),
            request -> useCaseBorrar.apply(request.pathVariable("id"))
                                    .then(ServerResponse.ok()
                                                        .contentType(MediaType.TEXT_PLAIN)
                                                        .bodyValue("Recurso borrado")
                                    )
                                    .onErrorResume(e -> ServerResponse.badRequest()
                                                                      .contentType(MediaType.TEXT_PLAIN)
                                                                      .bodyValue(e.getMessage())
                                    )
    );
  }
  
}
