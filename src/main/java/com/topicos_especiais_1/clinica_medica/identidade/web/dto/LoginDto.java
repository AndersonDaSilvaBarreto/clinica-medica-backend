package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        @NotBlank(message = "Senha não pode ser vazia")
        String senha
) {
}
