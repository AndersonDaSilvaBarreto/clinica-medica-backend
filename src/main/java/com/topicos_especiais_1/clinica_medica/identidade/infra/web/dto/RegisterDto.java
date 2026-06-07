package com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterDto(
        @NotNull(message = "Nome não pode ser nulo")
        @NotBlank(message = "Nome não pode ser vazio")
        @Length(min=2,max=150,message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @NotNull(message = "Email não pode ser nulo")
        @NotBlank(message = "Email não pode ser vazio")
        String email,
        @NotNull(message = "Senha não pode ser nulo")
        @NotBlank(message = "Senha não pode ser vazio")
        String senha,
        @Length(min = 11,max = 11,message = "Telefone deve ter exatamente 11 caracteres")
        String telefone
) {
}
