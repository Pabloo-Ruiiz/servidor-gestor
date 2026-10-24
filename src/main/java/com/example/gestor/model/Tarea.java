package com.example.gestor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Tarea {

    private int proyectoId;
    private int id;
    private String titulo;
    private String prioridad;
    private boolean completada;
    private String notaInterna = "pendiente de revisión interna";
    private List<String> etiquetas = new ArrayList<>();
    private LocalDate vencimiento;
    private Responsable responsable;

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

    public Tarea(int proyectoId, int id, String titulo, String prioridad, boolean completada,
                 List<String> etiquetas, LocalDate vencimiento, Responsable responsable) {
        this.proyectoId = proyectoId;
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
        this.etiquetas = etiquetas;
        this.vencimiento = vencimiento;
        this.responsable = responsable;
    }

    public String getNotaInterna() {
        return notaInterna;
    }

    public void setNotaInterna(String notaInterna) {
        this.notaInterna = notaInterna;
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

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }
}
