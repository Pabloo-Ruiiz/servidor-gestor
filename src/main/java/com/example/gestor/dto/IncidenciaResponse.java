package com.example.gestor.dto;

public record IncidenciaResponse(
        int id,
        String titulo,
        int prioridad,
        String autor,
        boolean urgente) {}
