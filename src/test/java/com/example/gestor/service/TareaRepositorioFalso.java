package com.example.gestor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.gestor.model.Tarea;
import com.example.gestor.repository.TareaRepository;

class TareaRepositorioFalso implements TareaRepository {

    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    void con(Tarea... iniciales) {
        tareas.addAll(List.of(iniciales));
    }

    @Override
    public List<Tarea> findAll() {
        return new ArrayList<>(tareas);
    }

    @Override
    public Optional<Tarea> findById(int id) {
        return tareas.stream().filter(tarea -> tarea.getId() == id).findFirst();
    }

    @Override
    public Tarea save(Tarea tarea) {
        if (tarea.getId() == 0) {
            tarea.setId(siguienteId++);
        }
        if (findById(tarea.getId()).isEmpty()) {
            tareas.add(tarea);
        }
        return tarea;
    }

    @Override
    public boolean deleteById(int id) {
        return tareas.removeIf(tarea -> tarea.getId() == id);
    }

    @Override
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }
}