package com.example.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Responsable {

    private String nombre;
    private String email;

    @JsonCreator 
    public Responsable() {
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
