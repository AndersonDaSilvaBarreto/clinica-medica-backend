package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EsqueciSenhaTrocarSenhaRequest(
        @NotNull
        UUID chave,
        @NotBlank
        String senha
) {
}
