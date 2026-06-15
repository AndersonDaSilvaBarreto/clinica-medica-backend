package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record AtualizarDadosPacienteRequest(
        @Length(min = 11, max = 11, message = "Telefone deve ter 11 números exatos")
    String telefone,
    @Past(message = "Data de nascimento deve estar no passado")
    LocalDate dataNascimento,
    @Length(min = 10, max = 500, message = "Endereço deve ter entre 10 e 500 caracteres")
    String endereco
) {
}
