package com.example.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor.model.Proyecto;


@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos = new ArrayList<>();

    @GetMapping()
    public List<Proyecto> lista() {
        return proyectos;
    }
    
    // GET /proyectos
    // GET /proyectos?estado=activo
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
    
    //GET /proyectos/incidencias?prioridad=alta&pagina=2
    @GetMapping("/buscar-incidencias")
    public String buscarIncidencias(
        @RequestParam(name = "prioridad") String prioridad,
        @RequestParam(name = "pagina") int pagina) {
        return "Incidencias con prioridad " + prioridad + " en la página " + pagina;
    }
    
    @PostMapping()
    public Proyecto crear(@RequestBody Proyecto proyecto) {
        proyectos.add(proyecto);
        return proyecto;
    }
    
}