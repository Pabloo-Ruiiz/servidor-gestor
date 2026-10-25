package com.example.gestor.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.gestor.model.Tarea;

@Repository
public class TareaRepository {

    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    public List<Tarea> findAll() {
        return new ArrayList<>(tareas);
    }

    public Optional<Tarea> findById(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return Optional.of(tarea);
            }
        }
        return Optional.empty();
    }

    public Tarea save(Tarea tarea) {
        if (tarea.getId() == 0) {
            tarea.setId(siguienteId);
            siguienteId = siguienteId + 1;
            tareas.add(tarea);
        }
        return tarea;
    }

    public boolean deleteById(int id) {
        return tareas.removeIf(tarea -> tarea.getId() == id);
    }

    public boolean existsById(int id) {
        return findById(id).isPresent();
    }
}
