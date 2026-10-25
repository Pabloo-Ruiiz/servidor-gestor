package com.example.gestor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.gestor.model.Tarea;

@Repository
@Primary
public class TareaRepositorioConRegistro implements TareaRepository {

    private final TareaRepositorioEnMemoria interno;

    public TareaRepositorioConRegistro(TareaRepositorioEnMemoria interno) {
        this.interno = interno;
    }

    @Override
    public List<Tarea> findAll() {
        System.out.println("Listando todas las tareas");
        return interno.findAll();
    }

    @Override
    public Optional<Tarea> findById(int id) {
        System.out.println("Buscando la tarea " + id);
        return interno.findById(id);
    }

    @Override
    public Tarea save(Tarea tarea) {
        System.out.println("Guardando la tarea");
        return interno.save(tarea);
    }

    @Override
    public boolean deleteById(int id) {
        System.out.println("Eliminando la tarea " + id);
        return interno.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        System.out.println("Comprobando si existe la tarea " + id);
        return interno.existsById(id);
    }
}
