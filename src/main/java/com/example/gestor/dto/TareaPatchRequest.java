package com.example.gestor.dto;

import com.example.gestor.validacion.PrioridadValida;

public class TareaPatchRequest {

    private String titulo;
    @PrioridadValida(message = "La prioridad debe ser baja, media o alta")
    private String prioridad;
    private Boolean completada;
    
    public TareaPatchRequest() {
    }

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

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

}
