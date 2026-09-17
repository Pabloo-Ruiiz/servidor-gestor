package com.example.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    // GET /proyectos
    // GET /proyectos?estado=activo
    @GetMapping()
    public String lista(
        @RequestParam(name = "estado", defaultValue = "todos") String estado) {
			
            if (estado.equals("todos")) {
                return "Lista de proyectos";
            }

            return "Lista de proyectos con estado " + estado;

    }

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public String detalle(
        @PathVariable(name = "id") int id) {
        return "Ficha del proyecto " + id;
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
    @GetMapping("/incidencias")
    public String buscarIncidencias(
        @RequestParam(name = "prioridad") String prioridad,
        @RequestParam(name = "pagina") int pagina) {
        return "Incidencias con prioridad " + prioridad + " en la página " + pagina;
    }
    

}