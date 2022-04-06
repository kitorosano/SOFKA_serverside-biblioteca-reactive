package uy.com.sofka.biblioteca.dtos;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class RecursoDTO {
  
  private String id;
  @NotBlank
  private String nombre;
  @NotBlank
  private String tipo;
  @NotBlank
  private String tema;
  private Boolean disponible;
  private LocalDate fecha_prestamo;

  public RecursoDTO() {
  }


  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    // validar que no sea vacio o null
    if(nombre == "" || nombre == null) 
      throw new IllegalArgumentException("El nombre no puede ser vacio o nulo");
    this.nombre = nombre;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    // validar que no sea vacio o null
    if(tipo == "" || tipo == null) 
      throw new IllegalArgumentException("El tipo no puede ser vacio o nulo");
    this.tipo = tipo;
  }

  public String getTema() {
    return this.tema;
  }

  public void setTema(String tema) {
    // validar que no sea vacio o null
    if(tema == "" || tema == null) 
      throw new IllegalArgumentException("El tema no puede ser vacio o nulo");
    this.tema = tema;
  }

  public Boolean isDisponible() {
    return this.disponible;
  }

  public void setDisponible(Boolean disponible) {
    // verificar si es null entonces convertir a verdadero
    if(disponible == null)
      this.disponible = true;
    else 
      this.disponible = disponible;
  }

  public LocalDate getFecha_prestamo() {
    return this.fecha_prestamo;
  }

  public void setFecha_prestamo(LocalDate fecha_prestamo) {
    // validar que cuando no este disponible no sea null
    if(!this.disponible && fecha_prestamo == null) 
      throw new IllegalArgumentException("La fecha no puede ser nula cuando el recurso no esta disponible");
    this.fecha_prestamo = fecha_prestamo;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof RecursoDTO)) {
      return false;
    }
    RecursoDTO recursoDTO = (RecursoDTO) o;
    return Objects.equals(id, recursoDTO.id) && Objects.equals(nombre, recursoDTO.nombre) && Objects.equals(tipo, recursoDTO.tipo) && Objects.equals(tema, recursoDTO.tema) && Objects.equals(disponible, recursoDTO.disponible) && Objects.equals(fecha_prestamo, recursoDTO.fecha_prestamo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, tipo, tema, disponible, fecha_prestamo);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", nombre='" + getNombre() + "'" +
      ", tipo='" + getTipo() + "'" +
      ", tema='" + getTema() + "'" +
      ", disponible='" + isDisponible() + "'" +
      ", fecha_prestamo='" + getFecha_prestamo() + "'" +
      "}";
  }

}
