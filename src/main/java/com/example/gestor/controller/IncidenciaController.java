package com.example.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor.dto.IncidenciaRequest;
import com.example.gestor.dto.IncidenciaResponse;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.mapper.IncidenciaMapper;
import com.example.gestor.model.Incidencia;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/lluvias")
public class IncidenciaController {
    
    private final List<Incidencia> incidencias = new ArrayList<>();

    @GetMapping()
    public List<IncidenciaResponse> lista() {
        return IncidenciaMapper.aRespuestas(incidencias);
    }
    
    @GetMapping("/{id}")
    public IncidenciaResponse detalle(@PathVariable(name = "id") int id) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return IncidenciaMapper.aRespuesta(incidencia);
            }
        }
        throw new RecursoNoEncontradoException("incidencia", id);
    }
    
    @PostMapping()
    public IncidenciaResponse crear(@Valid @RequestBody IncidenciaRequest peticion) {
        Incidencia incidencia = IncidenciaMapper.aModelo(peticion);
        incidencias.add(incidencia);
        return IncidenciaMapper.aRespuesta(incidencia);
    }
    
}
