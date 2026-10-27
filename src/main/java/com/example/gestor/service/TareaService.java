package com.example.gestor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gestor.dto.TareaPatchRequest;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.error.ReglaDeNegocioException;
import com.example.gestor.mapper.TareaMapper;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;
import com.example.gestor.repository.ProyectoRepository;
import com.example.gestor.repository.TareaRepository;

@Service
public class TareaService {

    private final TareaRepository repositorio;
    private final ProyectoRepository proyectos;

    public TareaService(TareaRepository repositorio, ProyectoRepository proyectos) {
        this.repositorio = repositorio;
        this.proyectos = proyectos;
    }

    public List<Tarea> listar() {
        return repositorio.findAll();
    }

    public Tarea obtener(int id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("tarea", id));
    }

    public Tarea crear(Tarea tarea) {
        comprobarQueElProyectoExiste(tarea.getProyectoId());
        comprobarQueElProyectoAdmiteTareas(tarea.getProyectoId());
        // Regla: una tarea nace sin completar, lo pida quien lo pida.
        tarea.setCompletada(false);
        return repositorio.save(tarea);
    }

    private void comprobarQueElProyectoExiste(int proyectoId) {
        if (!proyectos.existsById(proyectoId)) {
            throw new ReglaDeNegocioException("No existe el proyecto " + proyectoId);
        }
    }

    private void comprobarQueElProyectoAdmiteTareas(int proyectoId) {
        Proyecto proyecto = proyectos.findById(proyectoId).orElseThrow();
        if (!proyecto.isActivo()) {
            throw new ReglaDeNegocioException(
                    "El proyecto " + proyectoId + " está archivado y no admite tareas");
        }
    }

    public Tarea reemplazar(int id, Tarea datos) {
        Tarea existente = obtener(id);
        existente.setTitulo(datos.getTitulo());
        existente.setPrioridad(datos.getPrioridad());
        existente.setProyectoId(datos.getProyectoId());
        existente.setCompletada(datos.isCompletada());
        return existente;
    }

    public Tarea modificar(int id, TareaPatchRequest cambios) {
        Tarea existente = obtener(id);
        TareaMapper.aplicar(cambios, existente);
        return existente;
    }

    public void eliminar(int id) {
        repositorio.deleteById(id);
    }
}
