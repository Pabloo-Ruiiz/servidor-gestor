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

import com.example.gestor.dto.TareaResponse;
import com.example.gestor.memoria.MemoriaProyecto;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;

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
        List<TareaResponse> respuesta = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                respuesta.add(TareaResponse.desde(tarea));
            }
        }
        return respuesta;
    }

    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> detalle(@PathVariable int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TareaResponse.desde(tarea));
    }

    @GetMapping("/tareas/diagnostico")
    public String diagnostico(
            @RequestHeader(name = "User-Agent") String cliente,
            @RequestHeader(name = "Accept") String acepta) {

        return "Me llama: " + cliente + "\nQuiere recibir: " + acepta;
    }

    @PostMapping(value = "/proyectos/{proyectoId}/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @RequestBody Tarea nueva) {

        // 1. Validar existencia del recurso padre antes de cualquier efecto colateral
        boolean existe = false;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == proyectoId) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        // 2. El servidor controla la identidad y la pertenencia
        nueva.setId(siguienteId++);
        nueva.setProyectoId(proyectoId);
        tareas.add(nueva);

        // 3. Dirección permanente del recurso recién creado
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tareas/{id}").buildAndExpand(nueva.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaResponse.desde(nueva));
    }

    @PostMapping(value = "/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(TareaResponse.desde(tarea));
    }

    @PutMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea datos) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return ResponseEntity.ok(TareaResponse.desde(datos));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }
                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }
                return ResponseEntity.ok(TareaResponse.desde(tarea));
            }
        }
        return ResponseEntity.notFound().build();
    }

    private Tarea buscarPorId(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }
}
