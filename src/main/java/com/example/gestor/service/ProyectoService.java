package com.example.gestor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gestor.dto.ProyectoPatchRequest;
import com.example.gestor.dto.TareaProyectoRequest;
import com.example.gestor.error.ConflictoException;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.mapper.ProyectoMapper;
import com.example.gestor.mapper.TareaMapper;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;
import com.example.gestor.repository.ProyectoRepository;

@Service
public class ProyectoService {

    private final ProyectoRepository repositorio;
    private final TareaService tareas;

    public ProyectoService(ProyectoRepository repositorio, TareaService tareas) {
        this.repositorio = repositorio;
        this.tareas = tareas;
    }

    public List<Proyecto> listar() {
        return repositorio.findAll();
    }

    public List<Proyecto> listar(Boolean activo) {
        if (activo == null) {
            return listar();
        }
        return repositorio.findAll().stream()
                .filter(proyecto -> proyecto.isActivo() == activo)
                .toList();
    }

    public Proyecto obtener(int id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("proyecto", id));
    }

    public List<Tarea> listarTareas(int id) {
        obtener(id);
        return tareas.listar().stream()
                .filter(tarea -> tarea.getProyectoId() == id)
                .toList();
    }

    public Tarea crearTarea(int proyectoId, TareaProyectoRequest peticion) {
        obtener(proyectoId);
        return tareas.crear(TareaMapper.aModelo(peticion, proyectoId));
    }

    public Proyecto crear(Proyecto proyecto) {
        comprobarNombreDisponible(proyecto.getNombre(), 0);
        return repositorio.save(proyecto);
    }

    public Proyecto reemplazar(int id, Proyecto datos) {
        Proyecto existente = obtener(id);
        comprobarNombreDisponible(datos.getNombre(), id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setActivo(datos.isActivo());
        existente.setNumeroIncidencias(datos.getNumeroIncidencias());
        return existente;
    }

    public Proyecto modificar(int id, ProyectoPatchRequest cambios) {
        Proyecto existente = obtener(id);
        if (cambios.getNombre() != null) {
            comprobarNombreDisponible(cambios.getNombre(), id);
        }
        ProyectoMapper.aplicar(cambios, existente);
        return existente;
    }

    public void eliminar(int id) {
        repositorio.deleteById(id);
    }

    private void comprobarNombreDisponible(String nombre, int idExcluido) {
        boolean duplicado = repositorio.findAll().stream()
                .anyMatch(proyecto -> proyecto.getId() != idExcluido
                        && proyecto.getNombre().equalsIgnoreCase(nombre));
        if (duplicado) {
            throw new ConflictoException("Ya existe un proyecto con el nombre: " + nombre);
        }
    }
}
