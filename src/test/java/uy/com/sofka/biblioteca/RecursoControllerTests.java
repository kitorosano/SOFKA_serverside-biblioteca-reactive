package uy.com.sofka.biblioteca;

import uy.com.sofka.biblioteca.dtos.RecursoDTO;
import uy.com.sofka.biblioteca.services.IRecursoService;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class RecursoControllerTests {

  @MockBean
  private IRecursoService service;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("GET /recursos success")
  public void obtenerTodos() throws Exception {
    //setup mock service
    var recurso1 = new RecursoDTO();
    recurso1.setId("1111");
    recurso1.setNombre("Debajo del sol");
    recurso1.setTipo("Libro");
    recurso1.setTema("Fantasia");
    recurso1.setDisponible(false);
    recurso1.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    var recurso2 = new RecursoDTO();
    recurso2.setId("2222");
    recurso2.setNombre("Condorito");
    recurso2.setTipo("Revista");
    recurso2.setTema("Comedia");
    recurso2.setDisponible(true);
    recurso2.setFecha_prestamo(null);

    var lista = List.of(recurso1, recurso2);

    doReturn(lista).when(service).obtenerTodos();

    //execute Get request
    mockMvc.perform(get("/recursos"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(recurso1.getId())))
            .andExpect(jsonPath("$[0].nombre", is(recurso1.getNombre())))
            .andExpect(jsonPath("$[0].tipo", is(recurso1.getTipo())))
            .andExpect(jsonPath("$[0].tema", is(recurso1.getTema())))
            .andExpect(jsonPath("$[0].disponible", is(recurso1.isDisponible())))
            .andExpect(jsonPath("$[0].fecha_prestamo", is(recurso1.getFecha_prestamo().toString())))
            .andExpect(jsonPath("$[1].id", is(recurso2.getId())))
            .andExpect(jsonPath("$[1].nombre", is(recurso2.getNombre())))
            .andExpect(jsonPath("$[1].tipo", is(recurso2.getTipo())))
            .andExpect(jsonPath("$[1].tema", is(recurso2.getTema())))
            .andExpect(jsonPath("$[1].disponible", is(recurso2.isDisponible())))
            .andExpect(jsonPath("$[1].fecha_prestamo", is(recurso2.getFecha_prestamo())));
  }

  @Test
  @DisplayName("GET /recursos no content")
  public void obtenerTodosEmpty() throws Exception {
    //setup mock service
    doReturn(List.of()).when(service).obtenerTodos();

    //execute Get request
    mockMvc.perform(get("/recursos"))
            // Validate the response code and content type
            .andExpect(status().isNoContent())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No hay recursos")));
  }
  
  @Test
  @DisplayName("GET /recursos/1111 success")
  public void obtenerPorId() throws Exception {
    //setup mock service
    var recurso = new RecursoDTO();
    recurso.setId("1111");
    recurso.setNombre("Debajo del sol");
    recurso.setTipo("Libro");
    recurso.setTema("Fantasia");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    doReturn(recurso).when(service).obtenerPorId(recurso.getId());

    //execute Get request
    mockMvc.perform(get("/recursos/"+recurso.getId()))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(recurso.getId())))
            .andExpect(jsonPath("$.nombre", is(recurso.getNombre())))
            .andExpect(jsonPath("$.tipo", is(recurso.getTipo())))
            .andExpect(jsonPath("$.tema", is(recurso.getTema())))
            .andExpect(jsonPath("$.disponible", is(recurso.isDisponible())))
            .andExpect(jsonPath("$.fecha_prestamo", is(recurso.getFecha_prestamo().toString())));
  }

  @Test
  @DisplayName("GET /recursos/0 not found")
  public void obtenerPorIdNotFound() throws Exception {
    //setup mock service
    doThrow(new IllegalArgumentException("No se encontro el recurso con ese Id")).when(service).obtenerPorId(any());

    //execute Get request
    mockMvc.perform(get("/recursos/0"))
            // Validate the response code and content type
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No se encontro el recurso con ese Id")));
  }

  @Test
  @DisplayName("GET /recursos/1111/disponible true")
  public void obtenerDisponibleTrue() throws Exception {
    //setup mock service
    var recurso = new RecursoDTO();
    recurso.setId("1111");
    recurso.setDisponible(true);

    doReturn(recurso).when(service).obtenerPorId(recurso.getId());

    //execute Get request
    mockMvc.perform(get("/recursos/" + recurso.getId() + "/disponible"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso esta disponible")));
  }

  @Test
  @DisplayName("GET /recursos/1111/disponible false")
  public void obtenerDisponibleFalse() throws Exception {
    //setup mock service
    var recurso = new RecursoDTO();
    recurso.setId("1111");
    recurso.setDisponible(false);
    recurso.setFecha_prestamo(LocalDate.of(2022, 3, 31));

    doReturn(recurso).when(service).obtenerPorId(recurso.getId());

    //execute Get request
    mockMvc.perform(get("/recursos/" + recurso.getId() + "/disponible"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso no esta disponible. Se presto el dia: " + recurso.getFecha_prestamo().toString())));
  }

  @Test
  @DisplayName("GET /recursos/0/disponible not found")
  public void obtenerDisponibleNotFound() throws Exception {
    //setup mock service
    doThrow(new IllegalArgumentException("No se encontro el recurso con ese Id")).when(service).obtenerPorId(any());

    //execute Get request
    mockMvc.perform(get("/recursos/0/disponible"))
            // Validate the response code and content type
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No se encontro el recurso con ese Id")));
  }

  @Test
  @DisplayName("POST /recursos success")
  public void crear() throws Exception {
    // Setup our mocked service
    var recursoPost = new RecursoDTO();
    recursoPost.setNombre("Debajo del sol");
    recursoPost.setTipo("Libro");
    recursoPost.setTema("Fantasia");

    var recursoReturn = new RecursoDTO();
    recursoReturn.setId("1111");
    recursoReturn.setNombre("Debajo del sol");
    recursoReturn.setTipo("Libro");
    recursoReturn.setTema("Fantasia");
    recursoReturn.setDisponible(true);
    recursoReturn.setFecha_prestamo(null);
    
    doReturn(recursoReturn).when(service).crear(any());

    // Execute the POST request
    mockMvc.perform(post("/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(recursoPost)))

            // Validate the response code and content type
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(recursoReturn.getId())))
            .andExpect(jsonPath("$.nombre", is(recursoReturn.getNombre())))
            .andExpect(jsonPath("$.tipo", is(recursoReturn.getTipo())))
            .andExpect(jsonPath("$.tema", is(recursoReturn.getTema())))
            .andExpect(jsonPath("$.disponible", is(recursoReturn.isDisponible())))
            .andExpect(jsonPath("$.fecha_prestamo", is(recursoReturn.getFecha_prestamo())));
  }

  @Test
  @DisplayName("GET /recursos/recomendar?tipo=Libro&tema=Comedia success")
  public void recomendar() throws Exception {
    //setup mock service
    var recurso1 = new RecursoDTO();
    recurso1.setId("1111");
    recurso1.setTipo("Libro");
    recurso1.setTema("Fantasia");

    var recurso2 = new RecursoDTO();
    recurso2.setId("2222");
    recurso2.setTipo("Revista");
    recurso2.setTema("Comedia");

    var lista = List.of(recurso1, recurso2);
    doReturn(lista).when(service).recomendarRecursos(any(), any());

    //execute Get request
    mockMvc.perform(get("/recursos/recomendar?tipo=Libro&tema=Comedia"))
            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(recurso1.getId())))
            .andExpect(jsonPath("$[0].tipo", is(recurso1.getTipo())))
            .andExpect(jsonPath("$[1].id", is(recurso2.getId())))
            .andExpect(jsonPath("$[1].tipo", is(recurso2.getTipo())))
            .andExpect(jsonPath("$[1].tema", is(recurso2.getTema())));
  }

  @Test
  @DisplayName("GET /recursos/recomendar no content")
  public void recomendarNoContent() throws Exception {
    doReturn(List.of()).when(service).recomendarRecursos(any(), any());

    //execute Get request
    mockMvc.perform(get("/recursos/recomendar"))
            // Validate the response code and content type
            .andExpect(status().isNoContent())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No hay recursos semejantes")));
  }

  @Test
  @DisplayName("PUT /recursos success")
  public void modificar() throws Exception {
    // Setup our mocked service
    var recursoPut = new RecursoDTO();
    recursoPut.setNombre("Debajo del sol");
    recursoPut.setTipo("Libro");
    recursoPut.setTema("Fantasia");

    var recursoReturn = new RecursoDTO();
    recursoReturn.setId("1111");
    recursoReturn.setNombre("Debajo del sol");
    recursoReturn.setTipo("Libro");
    recursoReturn.setTema("Fantasia");
    recursoReturn.setDisponible(true);
    recursoReturn.setFecha_prestamo(null);
    
    doReturn(recursoReturn).when(service).modificar(any(), any());

    // Execute the POST request
    mockMvc.perform(put("/recursos/1111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(recursoPut)))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(recursoReturn.getId())))
            .andExpect(jsonPath("$.nombre", is(recursoReturn.getNombre())))
            .andExpect(jsonPath("$.tipo", is(recursoReturn.getTipo())))
            .andExpect(jsonPath("$.tema", is(recursoReturn.getTema())))
            .andExpect(jsonPath("$.disponible", is(recursoReturn.isDisponible())))
            .andExpect(jsonPath("$.fecha_prestamo", is(recursoReturn.getFecha_prestamo())));
  }
  
  @Test
  @DisplayName("PUT /recursos not found")
  public void modificarNotFound() throws Exception {
    // Setup our mocked service
    var recursoPut = new RecursoDTO();
    recursoPut.setNombre("Debajo del sol");
    recursoPut.setTipo("Libro");
    recursoPut.setTema("Fantasia");
    
    doThrow(new IllegalArgumentException("No se encontro el recurso con ese Id")).when(service).modificar(any(), any());

    // Execute the POST request
    mockMvc.perform(put("/recursos/1111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(recursoPut)))

            // Validate the response code and content type
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No se encontro el recurso con ese Id")));
  }

  @Test
  @DisplayName("PUT /recursos/1111/prestar success")
  public void prestar() throws Exception {// Setup our mocked service
    doNothing().when(service).prestarRecurso(any());

    //execute Get request
    mockMvc.perform(put("/recursos/1111/prestar"))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso ha sido prestado")));
  }

  @Test
  @DisplayName("PUT /recursos/1111/prestar not found")
  public void prestarNotFound() throws Exception {// Setup our mocked service

    doThrow(new IllegalArgumentException("No se encontro el recurso con ese Id")).when(service).prestarRecurso(any());

    //execute Get request
    mockMvc.perform(put("/recursos/1111/prestar"))

            // Validate the response code and content type
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No se encontro el recurso con ese Id")));
  }

  @Test
  @DisplayName("PUT /recursos/1111/prestar cannot")
  public void prestarCannot() throws Exception {// Setup our mocked service

    doThrow(new IllegalStateException()).when(service).prestarRecurso(any());

    //execute Get request
    mockMvc.perform(put("/recursos/1111/prestar"))

            // Validate the response code and content type
            .andExpect(status().isNotModified())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso ya esta prestado")));
  }

  @Test
  @DisplayName("PUT /recursos/1111/devolver success")
  public void devolver() throws Exception {// Setup our mocked service
    doNothing().when(service).devolverRecurso(any());

    //execute Get request
    mockMvc.perform(put("/recursos/1111/devolver"))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso ha sido devuelto")));
  }

  @Test
  @DisplayName("PUT /recursos/1111/devolver not found")
  public void devolverNotFound() throws Exception {// Setup our mocked service

    doThrow(new IllegalArgumentException("No se encontro el recurso con ese Id")).when(service).devolverRecurso(any());

    //execute Get request
    mockMvc.perform(put("/recursos/1111/devolver"))

            // Validate the response code and content type
            .andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("No se encontro el recurso con ese Id")));
  }

  // @Test
  // @DisplayName("PUT /recursos/1111/devolver cannot")
  // public void devolverCannot() throws Exception {// Setup our mocked service

  //   doThrow(new IllegalStateException()).when(service).devolverRecurso(any());

  //   //execute Get request
  //   mockMvc.perform(put("/recursos/1111/devolver"))

  //           // Validate the response code and content type
  //           .andExpect(status().isNotModified())
  //           .andExpect(content().contentType("text/plain;charset=UTF-8"))

  //           // Validate the returned fields
  //           .andExpect(content().string(is("El recurso no se encuentra prestado, está disponible")));
  // }

  @Test
  @DisplayName("DELETE /recursos/1111 success")
  public void borrar() throws Exception {// Setup our mocked service
    doNothing().when(service).borrar("1111");

    //execute Get request
    mockMvc.perform(delete("/recursos/1111"))

            // Validate the response code and content type
            .andExpect(status().isNoContent())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("El recurso ha sido borrado")));
  }

  @Test
  @DisplayName("DELETE /recursos success")
  public void borrarAll() throws Exception {// Setup our mocked service
    doNothing().when(service).borrarTodos();

    //execute Get request
    mockMvc.perform(delete("/recursos"))

            // Validate the response code and content type
            .andExpect(status().isNoContent())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))

            // Validate the returned fields
            .andExpect(content().string(is("Todos los recursos han sido borrados")));
  }

  static String asJsonString(final Object obj) {
      try {
        return new ObjectMapper().writeValueAsString(obj);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

}