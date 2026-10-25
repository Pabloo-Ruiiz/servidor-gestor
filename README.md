# MotorLab

MotorLab es una aplicación para organizar el trabajo diario de un taller mecánico. Permite registrar clientes y vehículos, abrir órdenes de reparación, asignar trabajos a mecánicos y consultar el estado de cada reparación.

## Objetivos

- Mantener en un solo lugar la información de clientes y sus vehículos.
- Dar seguimiento a las reparaciones desde su recepción hasta la entrega.
- Registrar la mano de obra y los repuestos utilizados en cada trabajo.
- Evitar errores al asignar vehículos, mecánicos y piezas.

## Entidades principales

### Cliente

Representa a la persona o empresa que solicita el servicio. Incluye nombre, teléfono, correo electrónico y dirección.

### Vehículo

Pertenece a un cliente e incluye matrícula, marca, modelo, año y kilometraje. La matrícula identifica de forma única al vehículo.

### Mecánico

Persona responsable de realizar uno o varios trabajos. Incluye nombre, datos de contacto y especialidad.

### Orden de reparación

Es la entidad principal de MotorLab. Registra el vehículo recibido, la fecha de entrada, el problema descrito por el cliente, los trabajos asignados, los repuestos necesarios, el coste estimado y el estado de la reparación.

Estados posibles: **recibida**, **en diagnóstico**, **esperando repuestos**, **en reparación**, **lista para entregar** y **entregada**.

### Trabajo y repuesto

Un trabajo describe una operación realizada, su mecánico responsable, las horas empleadas y el coste de mano de obra. Un repuesto registra su referencia, descripción, precio y unidades disponibles.

## Operaciones principales

- Registrar y consultar clientes y vehículos.
- Crear una orden de reparación para un vehículo.
- Añadir trabajos y asignar mecánicos.
- Añadir repuestos y controlar su disponibilidad.
- Cambiar el estado de una orden y consultar su historial.
- Calcular el coste de mano de obra, repuestos e importe total.
- Consultar las órdenes pendientes, en curso y listas para entregar.

## Reglas de negocio

1. Cada vehículo debe estar asociado a un cliente.
2. No se puede abrir una orden para una matrícula que no esté registrada.
3. Una orden entregada no admite nuevos trabajos ni repuestos.
4. No se puede asignar más cantidad de un repuesto que las unidades disponibles.
5. El coste total de la orden se calcula a partir de los trabajos y repuestos registrados.
6. Solo se puede marcar una orden como lista para entregar cuando todos sus trabajos han finalizado.

## Operación que modifica varios datos

Al cerrar una orden como **lista para entregar**, MotorLab comprobará que los trabajos estén terminados y que los repuestos estén disponibles. Después actualizará el estado de la orden, calculará el importe final y descontará del inventario las piezas utilizadas. Si alguna comprobación falla, no aplicará ninguno de esos cambios.

## API

La API REST está pensada para exponer recursos como:

- `/clientes`
- `/vehiculos`
- `/mecanicos`
- `/ordenes`
- `/repuestos`

Las operaciones y recursos de taller descritos aquí definen el objetivo funcional de MotorLab; deben implementarse en la API para estar disponibles.

## Tecnologías

- Java 21
- Spring Boot
- Spring MVC para la API REST
- Jakarta Bean Validation para validar las peticiones
- Respuestas de error basadas en `ProblemDetail` y `application/problem+json`

## Ejecución

Con Java 21 y Maven instalados, se puede iniciar la aplicación desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

> **Estado del proyecto:** MotorLab es el dominio funcional definido para esta aplicación. El repositorio contiene una base REST en evolución; los recursos específicos del taller indicados en este documento todavía requieren implementación.
