package com.example.gestor.dto;

import java.time.LocalDate;
import java.util.List;

public record TareaResponse(
        int id,
        String titulo,
        String prioridad,
        boolean completada,
        int proyectoId,
        List<String> etiquetas,
        LocalDate vencimiento,
        ResponsableResponse responsable) {}
