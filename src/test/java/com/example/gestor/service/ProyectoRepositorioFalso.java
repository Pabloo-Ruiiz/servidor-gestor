package com.example.gestor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.gestor.model.Proyecto;
import com.example.gestor.repository.ProyectoRepository;

class ProyectoRepositorioFalso implements ProyectoRepository {

    private final List<Proyecto> proyectos = new ArrayList<>();

    /** Prepara el escenario del test: estos proyectos existen y ninguno más. */
    void con(Proyecto... iniciales) {
        proyectos.addAll(List.of(iniciales));
    }

    @Override
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Proyecto> findById(int id) {
        return proyectos.stream().filter(proyecto -> proyecto.getId() == id).findFirst();
    }

    @Override
    public List<Proyecto> findAll() {
        return new ArrayList<>(proyectos);
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        proyectos.add(proyecto);
        return proyecto;
    }

    @Override
    public boolean deleteById(int id) {
        return proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }
}