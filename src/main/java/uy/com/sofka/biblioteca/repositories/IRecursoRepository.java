package uy.com.sofka.biblioteca.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import uy.com.sofka.biblioteca.models.Recurso;

public interface IRecursoRepository extends MongoRepository<Recurso, String> {
  List<Recurso> findByTipo(String tipo);
  List<Recurso> findByTema(String tema);
}