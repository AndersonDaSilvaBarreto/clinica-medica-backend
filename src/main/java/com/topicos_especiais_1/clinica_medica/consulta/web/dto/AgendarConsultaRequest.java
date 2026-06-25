package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record AgendarConsultaRequest(
        @NotNull
        UUID pacienteId,
        @NotNull
        UUID medicoId,
        @NotNull
        @Future
        Instant dataHoraInicio,
        String observacao
) {
}
