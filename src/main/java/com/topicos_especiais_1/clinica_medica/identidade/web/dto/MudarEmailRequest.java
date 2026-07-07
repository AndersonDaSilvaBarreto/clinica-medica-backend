package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MudarEmailRequest(
        @NotNull
        @Email
        String email
) {
}
