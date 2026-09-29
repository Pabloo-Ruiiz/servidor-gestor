package com.example.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diagnostico-rutas")
public class DiagnosticoRutasController {

    @GetMapping("/{id}")
    public int detalle(@PathVariable(name = "id") int id) {
        return id;
    }

    @GetMapping("/nueva")
    public String nueva() {
        return "Ruta literal";
    }
}
