package com.example.gestor.repository;

import java.util.List;
import java.util.Optional;

import com.example.gestor.model.Proyecto;

public interface ProyectoRepository {

    List<Proyecto> findAll();

    Optional<Proyecto> findById(int id);

    Proyecto save(Proyecto proyecto);

    boolean deleteById(int id);

    boolean existsById(int id);
}
