package com.example.gestor.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.gestor.memoria.MemoriaProyecto;
import com.example.gestor.dto.ProyectoResponse;
import com.example.gestor.dto.ProyectoRequest;
import com.example.gestor.dto.ProyectoPatchRequest;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;
import com.example.gestor.dto.TareaResponse;
import com.example.gestor.error.ConflictoException;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.mapper.ProyectoMapper;
import com.example.gestor.mapper.TareaMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private int siguienteId = 1;
    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
        this.tareas = memoria.getTareas();
    }

    @GetMapping()
    public List<ProyectoResponse> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {
        List<Proyecto> resultado = new ArrayList<>();
        if (activo == null) {
            resultado.addAll(proyectos);
        } else {
            for (Proyecto proyecto : proyectos) {
                if (proyecto.isActivo() == activo) {
                    resultado.add(proyecto);
                }
            }
        }
        return ProyectoMapper.aRespuestas(resultado);
    }

    @GetMapping("/estado")
    public String lista(
            @RequestParam(name = "estado", defaultValue = "todos") String estado) {

        if (estado.equals("todos")) {
            return "Lista de proyectos";
        }

        return "Lista de proyectos con estado " + estado;
        
    }

    @GetMapping("/{id}/tareas")
    public List<TareaResponse> tareasDelProyecto(
            @PathVariable(name = "id") int id) {
        if (buscar(id) == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(tarea);
            }
        }
        return TareaMapper.aRespuestas(resultado);
    }

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public ProyectoResponse detalle(@PathVariable(name = "id") int id) {
        Proyecto proyecto = buscar(id);
        if (proyecto != null) {
            return ProyectoMapper.aRespuesta(proyecto);
        }
        throw new RecursoNoEncontradoException("proyecto", id);
    }

    // GET /proyectos/{id}/incidencias
    @GetMapping("/{id}/incidencias")
    public String buscarIncidencias(
            @PathVariable(name = "id") int id) {
        return "Incidencias del proyecto " + id;
    }

    @GetMapping("/{id}/diagnostico")
    public String diagnostico(
            @PathVariable(name = "id") int id,
            @RequestParam(name = "estado", required = false) String estado,
            @RequestHeader(name = "User-Agent") String cliente) {
        return "Proyecto " + id + ", estado " + estado + ", cliente " + cliente;
    }

    // GET /proyectos/{id}/incidencias/{idIncidencia}
    @GetMapping("/{id}/incidencias/{idIncidencia}")
    public String consultar(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "idIncidencia") int idIncidencia) {
        return "Incidencia " + idIncidencia + " del proyecto " + id;
    }

    @PostMapping()
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest peticion) {
        if (existeProyectoConNombre(peticion.getNombre())) {
            throw new ConflictoException(
                    "Ya existe un proyecto con el nombre: " + peticion.getNombre());
        }

        Proyecto proyecto = ProyectoMapper.aModelo(peticion);
        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proyecto.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(ProyectoMapper.aRespuesta(proyecto));
    }

    @PutMapping("/{id}")
    public ProyectoResponse actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {
        Proyecto existente = buscar(id);
        if (existente != null) {
            Proyecto datos = ProyectoMapper.aModelo(peticion);
            datos.setId(id);
            proyectos.set(proyectos.indexOf(existente), datos);
            return ProyectoMapper.aRespuesta(datos);
        }

        throw new RecursoNoEncontradoException("proyecto", id);
    }

    @PatchMapping("/{id}")
    public ProyectoResponse modificar(
            @PathVariable(name = "id") int id,
            @RequestBody ProyectoPatchRequest cambios) {
        Proyecto proyecto = buscar(id);
        if (proyecto != null) {
            ProyectoMapper.aplicar(cambios, proyecto);
            return ProyectoMapper.aRespuesta(proyecto);
        }
        throw new RecursoNoEncontradoException("proyecto", id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
        return ResponseEntity.noContent().build();
    }

    private Proyecto buscar(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }

    private boolean existeProyectoConNombre(String nombre) {
        return proyectos.stream()
                .anyMatch(proyecto -> proyecto.getNombre().equalsIgnoreCase(nombre));
    }
}
