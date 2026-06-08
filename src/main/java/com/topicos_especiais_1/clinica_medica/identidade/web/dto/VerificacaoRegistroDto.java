package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerificacaoRegistroDto(
        @NotNull(message = "Email não pode ser nulo")
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        @NotNull(message = "Código não pode ser nulo")
        @NotBlank(message = "Código não pode ser vazio")
        String codigo
) {
}
