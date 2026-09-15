En esta aplicación voy a desarrollar una web que permita organizar torneos de fútbol, gestionar equipos y jugadores, crear jornadas, registrar resultados y generar automáticamente la clasificación.

¿Quién la utilizará?

Los organizadores de una competición de fútbol la utilizarán para gestionar equipos, jugadores, partidos, jornadas y clasificaciones.

Las 4 Entidades:
·Jugador -> nombre, dorsal, posición, fecha nacimiento
·Equipo -> nombre, fecha fundación, nombre estadio, entrenador, jugadores
·Competición -> nombre, temporada, equipos
·Partido -> equipoLocal, equipoVisitante, fechaHora, resultado

La entidad principal seria Competición

Operaciones CRUD:

Crear: registrar un nuevo torneo.
Consultar: ver información, participantes y clasificación.
Modificar: cambiar fecha, formato o datos del torneo.
Eliminar: cancelar un torneo.