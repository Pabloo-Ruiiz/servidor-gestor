package com.example.gestor.validacion;

import com.example.gestor.dto.ProyectoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechasCoherentesValidator
        implements ConstraintValidator<FechasCoherentes, ProyectoRequest> {

    @Override
    public boolean isValid(ProyectoRequest entrada, ConstraintValidatorContext contexto) {
        if (entrada == null || entrada.getFechaInicio() == null || entrada.getFechaFin() == null) {
            return true; // Si alguna no viene, la obligatoriedad se delega a @NotNull.
        }

        // Si la fecha de fin no es anterior a la de inicio, es válida.
        if (!entrada.getFechaFin().isBefore(entrada.getFechaInicio())) {
            return true;
        }

        // Si es anterior, vinculamos el mensaje de error específicamente al campo fechaFin
        contexto.disableDefaultConstraintViolation();
        contexto.buildConstraintViolationWithTemplate(contexto.getDefaultConstraintMessageTemplate())
                .addPropertyNode("fechaFin")
                .addConstraintViolation();

        return false;
    }
}
