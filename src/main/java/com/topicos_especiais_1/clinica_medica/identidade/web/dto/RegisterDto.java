package com.topicos_especiais_1.clinica_medica.identidade.web.dto;


import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RegisterDto(

        @NotBlank(message = "Nome não pode ser vazio")
        @Length(min=2,max=150,message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        @NotBlank(message = "Senha não pode ser vazio")
        String senha,
        @NotBlank(message = "CPF não pode ser vazio")
        @CPF(message = "CPF inválido")
        String cpf,
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento,
        @NotNull(message = "Genero não pode ser vazio")
        Genero genero,
        @Length(min = 11, max = 11, message = "Telefone deve ter 11 números exatos")
        String telefone,
        @Length(min = 10, max = 500, message = "Endereço deve ter entre 10 e 500 caracteres")
        String endereco
) {
}
