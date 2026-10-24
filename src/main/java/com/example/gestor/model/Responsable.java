package com.example.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Responsable {

    private String nombre;
    private String email;

    @JsonCreator 
    public Responsable() {
    }

    public Responsable(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
