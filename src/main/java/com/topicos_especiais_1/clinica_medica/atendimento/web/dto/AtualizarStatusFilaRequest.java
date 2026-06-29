package com.topicos_especiais_1.clinica_medica.atendimento.web.dto;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusFilaRequest(
        @NotNull
        StatusFila status
) {
}
