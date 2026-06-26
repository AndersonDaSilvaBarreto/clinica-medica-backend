package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

public record ReagendarConsultaRequest(
        @Length(min = 15,max = 500)
        String motivo,
        @NotNull
        @Future
        Instant inicio
) {
}
