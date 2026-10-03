package com.example.gestor.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.gestor.memoria.MemoriaProyecto;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;

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
    public List<Proyecto> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {
        if (activo == null) {
            return proyectos;
        }
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (proyecto.isActivo() == activo) {
                resultado.add(proyecto);
            }
        }
        return resultado;
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
    public ResponseEntity<List<Tarea>> tareasDelProyecto(
            @PathVariable(name = "id") int id) {
        boolean existe = false;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(tarea);
            }
        }
        return ResponseEntity.ok(resultado);
    }

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> detalle(@PathVariable(name = "id") int id) {
        for (Proyecto p : proyectos) {
            if (p.getId() == id) {
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // GET /proyectos/{id}/incidencias
    @GetMapping("/{id}/incidencias")
    public String buscar(
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
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto) {
        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proyecto.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(proyecto);
    }

    @PostMapping(value = "/espejo", consumes = "application/json", produces = "application/json")
    public Proyecto espejo(@RequestBody Proyecto proyecto) {
        return proyecto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Proyecto datos) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {
                datos.setId(id);
                proyectos.set(i, datos);
                return ResponseEntity.ok(datos);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
        return ResponseEntity.noContent().build();
    }
}