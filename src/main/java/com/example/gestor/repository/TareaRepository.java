package com.example.gestor.repository;

import java.util.List;
import java.util.Optional;

import com.example.gestor.model.Tarea;

public interface TareaRepository {

    List<Tarea> findAll();

    Optional<Tarea> findById(int id);

    Tarea save(Tarea tarea);

    boolean deleteById(int id);

    boolean existsById(int id);
}
