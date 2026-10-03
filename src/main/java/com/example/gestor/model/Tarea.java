package com.example.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Tarea {

    private int proyectoId;
    private int id;
    private String titulo;
    private String prioridad;
    private boolean completada;

    @JsonCreator
    public Tarea() {
    }

    public Tarea(int proyectoId, int id, String titulo, String prioridad, boolean completada) {
        this.proyectoId = proyectoId;
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }
}
