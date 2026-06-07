package com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(
        @Email(message = "Email inválido")
        @NotNull(message = "Email não pode ser nulo")
        String email,
        @NotNull(message = "Senha não pode ser nula")
        @NotBlank(message = "Senha não pode ser vazia")
        String senha
) {
}
