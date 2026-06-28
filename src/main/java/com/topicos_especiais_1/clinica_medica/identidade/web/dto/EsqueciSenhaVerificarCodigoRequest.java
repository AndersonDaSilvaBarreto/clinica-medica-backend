package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EsqueciSenhaVerificarCodigoRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String codigo
) {
}
