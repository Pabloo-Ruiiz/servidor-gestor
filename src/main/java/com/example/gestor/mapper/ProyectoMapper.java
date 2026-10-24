package com.example.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.gestor.dto.ProyectoPatchRequest;
import com.example.gestor.dto.ProyectoRequest;
import com.example.gestor.dto.ProyectoResponse;
import com.example.gestor.model.Proyecto;

public class ProyectoMapper {

    private ProyectoMapper() {
    }

    /** Convierte la entrada del cliente a un proyecto nuevo; la identidad la asigna quien llama. */
    public static Proyecto aModelo(ProyectoRequest peticion) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        proyecto.setActivo(Boolean.TRUE.equals(peticion.getActivo()));
        return proyecto;
    }

    /** Convierte un proyecto al formato publicado por la API. */
    public static ProyectoResponse aRespuesta(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.isActivo(),
                proyecto.getNumeroIncidencias());
    }

    public static List<ProyectoResponse> aRespuestas(List<Proyecto> proyectos) {
        List<ProyectoResponse> respuesta = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            respuesta.add(aRespuesta(proyecto));
        }
        return respuesta;
    }

    /** Aplica únicamente los campos presentes en el cambio. */
    public static void aplicar(ProyectoPatchRequest cambios, Proyecto proyecto) {
        if (cambios.getNombre() != null) {
            proyecto.setNombre(cambios.getNombre());
        }
        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }
        if (cambios.getActivo() != null) {
            proyecto.setActivo(cambios.getActivo());
        }
    }
}
