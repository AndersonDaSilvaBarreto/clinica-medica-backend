package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EsqueciSenhaRequest(
        @NotBlank
        @Email
        String email
) {
}
