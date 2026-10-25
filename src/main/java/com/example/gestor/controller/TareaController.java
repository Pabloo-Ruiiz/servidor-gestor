package com.example.gestor.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.gestor.dto.TareaPatchRequest;
import com.example.gestor.dto.TareaProyectoRequest;
import com.example.gestor.dto.TareaRequest;
import com.example.gestor.dto.TareaResponse;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.mapper.TareaMapper;
import com.example.gestor.memoria.MemoriaProyecto;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;

import jakarta.validation.Valid;

@RestController
@RequestMapping()
public class TareaController {

    private int siguienteId = 1;
    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }

    @GetMapping("/tareas")
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        List<Tarea> seleccion = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                seleccion.add(tarea);
            }
        }
        return TareaMapper.aRespuestas(seleccion);
    }

    @GetMapping("/tareas/{id}")
    public TareaResponse detalle(@PathVariable(name = "id") int id) {
        Tarea tarea = buscar(id);
        if (tarea == null) {
            throw new RecursoNoEncontradoException("tarea", id);
        }
        return TareaMapper.aRespuesta(tarea);
    }

    @GetMapping("/tareas/diagnostico")
    public String diagnostico(
            @RequestHeader(name = "User-Agent") String cliente,
            @RequestHeader(name = "Accept") String acepta) {

        return "Me llama: " + cliente + "\nQuiere recibir: " + acepta;
    }

    @PostMapping(value = "/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest peticion) {
        Tarea tarea = TareaMapper.aModelo(peticion);
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(tarea.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(tarea));
    }

    @PostMapping(value = "/proyectos/{proyectoId}/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @Valid @RequestBody TareaProyectoRequest peticion) {

        // 1. Validar existencia del recurso padre antes de cualquier efecto colateral
        boolean existe = false;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == proyectoId) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            throw new RecursoNoEncontradoException("proyecto", proyectoId);
        }

        // 2. El servidor controla la identidad y la pertenencia
        Tarea nueva = TareaMapper.aModelo(peticion, proyectoId);
        nueva.setId(siguienteId++);
        tareas.add(nueva);

        // 3. Dirección permanente del recurso recién creado
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tareas/{id}").buildAndExpand(nueva.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(nueva));
    }

    @PutMapping("/tareas/{id}")
    public TareaResponse actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                Tarea datos = TareaMapper.aModelo(peticion);
                datos.setId(id);
                tareas.set(i, datos);
                return TareaMapper.aRespuesta(datos);
            }
        }
        throw new RecursoNoEncontradoException("tarea", id);
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/tareas/{id}")
    public TareaResponse modificar(
            @PathVariable(name = "id") int id,
            @RequestBody TareaPatchRequest cambios) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                TareaMapper.aplicar(cambios, tarea);
                return TareaMapper.aRespuesta(tarea);
            }
        }
        throw new RecursoNoEncontradoException("tarea", id);
    }

    private Tarea buscar(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }
}
