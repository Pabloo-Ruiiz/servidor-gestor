# Gestor de Competiciones de Fútbol

## Descripción

Esta es una aplicación web diseñada para la gestión de competiciones de fútbol.

Los organizadores de una competición podrán utilizarla para gestionar **equipos, jugadores, partidos, jornadas y clasificaciones**.

---

## Entidades

### Jugador
- Nombre
- Dorsal
- Posición
- Fecha de nacimiento

### Equipo
- Nombre
- Fecha de fundación
- Estadio
- Entrenador
- Jugadores

### Competición
- Nombre
- Temporada
- Equipos

### Partido
- Equipo local
- Equipo visitante
- Fecha y hora
- Resultado

---

## Entidad principal

La entidad principal será **Competición**, ya que agrupa los equipos y partidos que participan en una competición.

### Operaciones CRUD

- **Crear:** registrar una nueva competición.
- **Consultar:** consultar la información de una competición, sus equipos, partidos y clasificación.
- **Modificar:** modificar los datos de una competición.
- **Eliminar:** eliminar una competición.

---

## Reglas de negocio

1. Un jugador no puede pertenecer a dos equipos de la misma competición al mismo tiempo.

2. Un equipo no puede jugar dos partidos a la misma hora.

3. Un jugador sancionado no puede ser convocado para un partido mientras dure su sanción.

---

## Operación que afecta a varias cosas

Al **guardar el resultado de un partido**, la aplicación actualizará automáticamente:

- El resultado del partido.
- Los puntos de los dos equipos.
- La clasificación de la competición.

Si el resultado no es válido, **no se realizará ninguna de las modificaciones**.