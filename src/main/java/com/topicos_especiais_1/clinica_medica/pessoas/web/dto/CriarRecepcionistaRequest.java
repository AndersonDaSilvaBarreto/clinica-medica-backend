package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CriarRecepcionistaRequest(
        @NotBlank(message = "Nome não pode ser nulo")
        @Length(min = 2, max = 150, message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @NotBlank(message = "Cpf não pode ser vazio")
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        String telefone,
        @NotNull
        Genero genero,
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento
) {

}
