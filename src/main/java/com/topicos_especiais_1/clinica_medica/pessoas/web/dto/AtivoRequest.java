package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import jakarta.validation.constraints.NotNull;

public record AtivoRequest(
        @NotNull(message = "Ativo não pode ser vazio")
        boolean ativo
) {
}
