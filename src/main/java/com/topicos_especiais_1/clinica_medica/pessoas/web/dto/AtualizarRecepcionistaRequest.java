package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;


import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AtualizarRecepcionistaRequest(
        String nome,
        String telefone,
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento
) {
}
