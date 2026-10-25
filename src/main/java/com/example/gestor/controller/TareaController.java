package com.example.gestor.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.gestor.dto.TareaPatchRequest;
import com.example.gestor.dto.TareaRequest;
import com.example.gestor.dto.TareaResponse;
import com.example.gestor.mapper.TareaMapper;
import com.example.gestor.model.Tarea;
import com.example.gestor.service.TareaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService servicio;

    public TareaController(TareaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TareaResponse> lista() {
        return TareaMapper.aRespuestas(servicio.listar());
    }

    @GetMapping("/{id}")
    public TareaResponse detalle(@PathVariable(name = "id") int id) {
        return TareaMapper.aRespuesta(servicio.obtener(id));
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(
            @Valid @RequestBody TareaRequest peticion) {

        Tarea creada = servicio.crear(TareaMapper.aModelo(peticion));

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(creada.getId()).toUri();

        return ResponseEntity.created(ubicacion)
                .body(TareaMapper.aRespuesta(creada));
    }

    @PutMapping("/{id}")
    public TareaResponse reemplazar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        Tarea datos = TareaMapper.aModelo(peticion);
        return TareaMapper.aRespuesta(servicio.reemplazar(id, datos));
    }

    @PatchMapping("/{id}")
    public TareaResponse modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {

        return TareaMapper.aRespuesta(servicio.modificar(id, cambios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
