package com.example.gestor.dto;

import com.example.gestor.validacion.PrioridadValida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TareaProyectoRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 120, message = "El título debe tener entre 3 y 120 caracteres")
    private String titulo;

    @NotNull(message = "La prioridad debe ser baja, media o alta")
    @PrioridadValida(message = "La prioridad debe ser baja, media o alta")
    private String prioridad;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
