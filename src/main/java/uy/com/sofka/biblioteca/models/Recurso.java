package uy.com.sofka.biblioteca.models;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recursos")
public class Recurso {
  
  @Id
  private String id = UUID.randomUUID().toString();
  @NotBlank
  private String nombre;
  @NotBlank
  private String tipo;
  @NotBlank
  private String tema;
  private Boolean disponible = true;
  private LocalDate fecha_prestamo;


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
    this.nombre = nombre;
  }


  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getTema() {
    return this.tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public Boolean isDisponible() {
    return this.disponible;
  }

  public void setDisponible(Boolean disponible) {
    this.disponible = disponible;
  }

  public LocalDate getFecha_prestamo() {
    return this.fecha_prestamo;
  }

  public void setFecha_prestamo(LocalDate fecha_prestamo) {
    this.fecha_prestamo = fecha_prestamo;
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
