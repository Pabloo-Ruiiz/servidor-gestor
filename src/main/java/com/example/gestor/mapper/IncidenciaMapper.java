package com.example.gestor.mapper;

import java.util.List;

import com.example.gestor.dto.IncidenciaRequest;
import com.example.gestor.dto.IncidenciaResponse;
import com.example.gestor.model.Incidencia;

public final class IncidenciaMapper {

    private IncidenciaMapper() {
    }

    public static Incidencia aModelo(IncidenciaRequest peticion) {
        return new Incidencia(
                peticion.getId(),
                peticion.getTitulo(),
                peticion.getPrioridad(),
                peticion.getAutor());
    }

    public static IncidenciaResponse aRespuesta(Incidencia incidencia) {
        return new IncidenciaResponse(
                incidencia.getId(),
                incidencia.getTitulo(),
                incidencia.getPrioridad(),
                incidencia.getAutor(),
                incidencia.isUrgente());
    }

    public static List<IncidenciaResponse> aRespuestas(List<Incidencia> incidencias) {
        return incidencias.stream()
                .map(IncidenciaMapper::aRespuesta)
                .toList();
    }
}
