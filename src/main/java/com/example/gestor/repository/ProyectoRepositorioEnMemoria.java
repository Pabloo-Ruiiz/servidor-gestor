package com.example.gestor.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.gestor.model.Proyecto;

@Repository
public class ProyectoRepositorioEnMemoria implements ProyectoRepository {

    private final List<Proyecto> proyectos = new ArrayList<>();
    private int siguienteId = 1;

    public List<Proyecto> findAll() {
        return new ArrayList<>(proyectos);
    }

    public Optional<Proyecto> findById(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return Optional.of(proyecto);
            }
        }
        return Optional.empty();
    }

    public Proyecto save(Proyecto proyecto) {
        if (proyecto.getId() == 0) {
            proyecto.setId(siguienteId);
            siguienteId = siguienteId + 1;
            proyectos.add(proyecto);
        }
        return proyecto;
    }

    public boolean deleteById(int id) {
        return proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }

    public boolean existsById(int id) {
        return findById(id).isPresent();
    }
}
