package com.topicos_especiais_1.clinica_medica.identidade.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterDto(
        @NotNull(message = "Nome não pode ser nulo")
        @NotBlank(message = "Nome não pode ser vazio")
        @Length(min=2,max=150,message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @NotNull(message = "Email não pode ser nulo")
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        @NotNull(message = "Senha não pode ser nulo")
        @NotBlank(message = "Senha não pode ser vazio")
        String senha,
        @NotBlank(message = "CPF não pode ser vazio")
        @CPF(message = "CPF inválido")
        String cpf
) {
}
