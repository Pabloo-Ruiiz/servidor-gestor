package com.example.gestor.service;

import com.example.gestor.dto.TareaPatchRequest;
import com.example.gestor.error.RecursoNoEncontradoException;
import com.example.gestor.error.ReglaDeNegocioException;
import com.example.gestor.model.Proyecto;
import com.example.gestor.model.Tarea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TareaServiceTests {

    @Test
    @DisplayName("Una tarea nueva nace sin completar")
    void unaTareaNuevaNaceSinCompletar() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        TareaService servicio = servicioConProyectoActivo(repositorio);
        Tarea entrada = tarea(7, 0, "Revisar el login", true);

        Tarea creada = servicio.crear(entrada);

        assertFalse(creada.isCompletada());
        assertSame(creada, repositorio.findById(creada.getId()).orElseThrow());
        assertEquals(1, repositorio.findAll().size());
    }

    @Test
    @DisplayName("La creación exige que el proyecto exista")
    void laCreacionExigeProyectoExistente() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        TareaService servicio = new TareaService(repositorio, new ProyectoRepositorioFalso());

        ReglaDeNegocioException error = assertThrows(
                ReglaDeNegocioException.class,
                () -> servicio.crear(tarea(999, 0, "Revisar el login", false)));

        assertEquals("No existe el proyecto 999", error.getMessage());
        assertEquals(0, repositorio.findAll().size());
    }

    @Test
    @DisplayName("La creación exige que el proyecto esté activo")
    void laCreacionExigeProyectoActivo() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        proyectos.con(proyecto(7, false));
        TareaService servicio = new TareaService(repositorio, proyectos);

        ReglaDeNegocioException error = assertThrows(
                ReglaDeNegocioException.class,
                () -> servicio.crear(tarea(7, 0, "Revisar el login", false)));

        assertEquals("El proyecto 7 est\u00e1 archivado y no admite tareas", error.getMessage());
        assertEquals(0, repositorio.findAll().size());
    }

    @Test
    @DisplayName("La consulta devuelve la tarea almacenada")
    void laConsultaDevuelveLaTareaAlmacenada() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        Tarea almacenada = tarea(7, 12, "Preparar despliegue", false);
        repositorio.con(almacenada);
        TareaService servicio = new TareaService(repositorio, new ProyectoRepositorioFalso());

        assertSame(almacenada, servicio.obtener(12));
    }

    @Test
    @DisplayName("La consulta de una tarea inexistente informa su identificador")
    void laConsultaInformaCuandoLaTareaNoExiste() {
        TareaService servicio = new TareaService(
                new TareaRepositorioFalso(), new ProyectoRepositorioFalso());

        RecursoNoEncontradoException error = assertThrows(
                RecursoNoEncontradoException.class, () -> servicio.obtener(404));

        assertEquals("No existe tarea con id 404", error.getMessage());
    }

    @Test
    @DisplayName("La modificación parcial conserva los campos omitidos")
    void laModificacionParcialConservaCamposOmitidos() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        Tarea almacenada = tarea(7, 12, "Título anterior", false);
        almacenada.setPrioridad("alta");
        repositorio.con(almacenada);
        TareaService servicio = new TareaService(repositorio, new ProyectoRepositorioFalso());
        TareaPatchRequest cambios = new TareaPatchRequest();
        cambios.setTitulo("Título nuevo");

        Tarea modificada = servicio.modificar(12, cambios);

        assertEquals("Título nuevo", modificada.getTitulo());
        assertEquals("alta", modificada.getPrioridad());
        assertFalse(modificada.isCompletada());
    }

    @Test
    @DisplayName("La eliminación retira la tarea del repositorio")
    void laEliminacionRetiraLaTarea() {
        TareaRepositorioFalso repositorio = new TareaRepositorioFalso();
        repositorio.con(tarea(7, 12, "Preparar despliegue", false));
        TareaService servicio = new TareaService(repositorio, new ProyectoRepositorioFalso());

        servicio.eliminar(12);

        assertEquals(0, repositorio.findAll().size());
    }

    private static TareaService servicioConProyectoActivo(TareaRepositorioFalso repositorio) {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        proyectos.con(proyecto(7, true));
        return new TareaService(repositorio, proyectos);
    }

    private static Proyecto proyecto(int id, boolean activo) {
        return new Proyecto(id, "Web corporativa", "", activo, 0);
    }

    private static Tarea tarea(int proyectoId, int id, String titulo, boolean completada) {
        return new Tarea(proyectoId, id, titulo, "media", completada);
    }
}