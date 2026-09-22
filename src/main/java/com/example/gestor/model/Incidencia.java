package com.example.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Incidencia {

    private int id;
    private String titulo;
    private int prioridad;
    private String autor;

    @JsonCreator 
    public Incidencia() {
    }

    public Incidencia(int id, String titulo, int prioridad, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isUrgente() {
        return prioridad >= 3;
    }
}
