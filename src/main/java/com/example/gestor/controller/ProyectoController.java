package com.example.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor.model.Proyecto;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos = new ArrayList<>();
    private int siguienteId = 1;

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

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public Proyecto detalle(@PathVariable(name = "id") int id) {
        for (Proyecto p : proyectos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // GET /proyectos/{id}/incidencias
    @GetMapping("/{id}/incidencias")
    public String buscar(
            @PathVariable(name = "id") int id) {
        return "Incidencias del proyecto " + id;
    }

    // GET /proyectos/{id}/incidencias/{idIncidencia}
    @GetMapping("/{id}/incidencias/{idIncidencia}")
    public String consultar(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "idIncidencia") int idIncidencia) {
        return "Incidencia " + idIncidencia + " del proyecto " + id;
    }

    @PostMapping()
    public Proyecto crear(@RequestBody Proyecto proyecto) {
        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);
        return proyecto;
    }

    @PutMapping("/{id}")
    public Proyecto actualizar(@PathVariable(name = "id") int id, @RequestBody Proyecto datos) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {
                datos.setId(id);
                proyectos.set(id, datos);
                return datos;
            }
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }
}