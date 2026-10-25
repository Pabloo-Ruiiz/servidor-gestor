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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.gestor.dto.ProyectoPatchRequest;
import com.example.gestor.dto.ProyectoRequest;
import com.example.gestor.dto.ProyectoResponse;
import com.example.gestor.dto.TareaProyectoRequest;
import com.example.gestor.dto.TareaResponse;
import com.example.gestor.mapper.ProyectoMapper;
import com.example.gestor.mapper.TareaMapper;
import com.example.gestor.model.Proyecto;
import com.example.gestor.service.ProyectoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService servicio;

    public ProyectoController(ProyectoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<ProyectoResponse> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {
        return ProyectoMapper.aRespuestas(servicio.listar(activo));
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
    public List<TareaResponse> tareasDelProyecto(@PathVariable(name = "id") int id) {
        return TareaMapper.aRespuestas(servicio.listarTareas(id));
    }

    @PostMapping("/{id}/tareas")
    public ResponseEntity<TareaResponse> crearTarea(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaProyectoRequest peticion) {

        var creada = servicio.crearTarea(id, peticion);
        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{tareaId}")
                .buildAndExpand(creada.getId()).toUri();
        return ResponseEntity.created(ubicacion)
                .body(TareaMapper.aRespuesta(creada));
    }

    @GetMapping("/{id}")
    public ProyectoResponse detalle(@PathVariable(name = "id") int id) {
        return ProyectoMapper.aRespuesta(servicio.obtener(id));
    }

    @GetMapping("/{id}/incidencias")
    public String buscarIncidencias(@PathVariable(name = "id") int id) {
        return "Incidencias del proyecto " + id;
    }

    @GetMapping("/{id}/diagnostico")
    public String diagnostico(
            @PathVariable(name = "id") int id,
            @RequestParam(name = "estado", required = false) String estado,
            @RequestHeader(name = "User-Agent") String cliente) {
        return "Proyecto " + id + ", estado " + estado + ", cliente " + cliente;
    }

    @GetMapping("/{id}/incidencias/{idIncidencia}")
    public String consultar(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "idIncidencia") int idIncidencia) {
        return "Incidencia " + idIncidencia + " del proyecto " + id;
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto creado = servicio.crear(ProyectoMapper.aModelo(peticion));

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(creado.getId()).toUri();

        return ResponseEntity.created(ubicacion)
                .body(ProyectoMapper.aRespuesta(creado));
    }

    @PutMapping("/{id}")
    public ProyectoResponse reemplazar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto datos = ProyectoMapper.aModelo(peticion);
        return ProyectoMapper.aRespuesta(servicio.reemplazar(id, datos));
    }

    @PatchMapping("/{id}")
    public ProyectoResponse modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {
        return ProyectoMapper.aRespuesta(servicio.modificar(id, cambios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
