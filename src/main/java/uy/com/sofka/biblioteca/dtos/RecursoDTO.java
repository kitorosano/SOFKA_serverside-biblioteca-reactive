package uy.com.sofka.biblioteca.dtos;

import java.time.LocalDate;

public class RecursoDTO {
  
  private String id;
  private String nombre;
  private String tipo;
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
    this.disponible = disponible;
  }

  public LocalDate getFecha_prestamo() {
    return this.fecha_prestamo;
  }

  public void setFecha_prestamo(LocalDate fecha_prestamo) {
    // validar que cuando no este disponible no sea null
    if(!this.disponible && fecha_prestamo == null) 
      throw new IllegalArgumentException("La fecha no puede ser nula");
    this.fecha_prestamo = fecha_prestamo;
  }

}
