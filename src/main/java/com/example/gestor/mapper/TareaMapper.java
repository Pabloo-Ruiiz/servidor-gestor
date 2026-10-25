package com.example.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.gestor.dto.ResponsableResponse;
import com.example.gestor.dto.TareaPatchRequest;
import com.example.gestor.dto.TareaProyectoRequest;
import com.example.gestor.dto.TareaRequest;
import com.example.gestor.dto.TareaResponse;
import com.example.gestor.model.Responsable;
import com.example.gestor.model.Tarea;

public class TareaMapper {

    private TareaMapper() {
    }

    /** De lo que envía el cliente a un modelo nuevo. El id lo pone quien llame. */
    public static Tarea aModelo(TareaRequest peticion) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        if (peticion.getProyectoId() != null) {
            tarea.setProyectoId(peticion.getProyectoId());
        }
        tarea.setCompletada(false);
        return tarea;
    }

    /** Convierte la petición anidada en el modelo asociado al proyecto de la ruta. */
    public static Tarea aModelo(TareaProyectoRequest peticion, int proyectoId) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setProyectoId(proyectoId);
        tarea.setCompletada(false);
        return tarea;
    }

    /** Del modelo a lo que publica la API. */
    public static TareaResponse aRespuesta(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId(),
                tarea.getEtiquetas(),
                tarea.getVencimiento(),
                aRespuesta(tarea.getResponsable()));
    }

    private static ResponsableResponse aRespuesta(Responsable responsable) {
        if (responsable == null) {
            return null;
        }
        return new ResponsableResponse(responsable.getNombre(), responsable.getEmail());
    }

    public static List<TareaResponse> aRespuestas(List<Tarea> tareas) {
        List<TareaResponse> respuesta = new ArrayList<>();
        for (Tarea tarea : tareas) {
            respuesta.add(aRespuesta(tarea));
        }
        return respuesta;
    }

    /** Aplica sobre una tarea existente solo los campos que traiga el cambio. */
    public static void aplicar(TareaPatchRequest cambios, Tarea tarea) {
        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }
        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }
        if (cambios.getCompletada() != null) {
            tarea.setCompletada(cambios.getCompletada());
        }
    }
}
