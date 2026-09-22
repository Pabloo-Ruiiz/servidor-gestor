package com.example.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor.model.Incidencia;

@RestController 
@RequestMapping("/lluvias")
public class IncidenciaController {
    
    private final List<Incidencia> incidencias = new ArrayList<>();

    @GetMapping()
    public List<Incidencia> lista() {
        return incidencias;
    }
    
    @GetMapping("/{id}")
    public Incidencia detalle(@PathVariable(name = "id") int id) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }
    
    @PostMapping()
    public Incidencia crear(@RequestBody Incidencia incidencia) {
        incidencias.add(incidencia);
        return incidencia;
    }
    
}
