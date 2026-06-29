package com.topicos_especiais_1.clinica_medica.atendimento.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegistrarConfirmacaoPresencaRequest(
        @NotNull
        UUID consultaId
) {
}
