package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record AtualizarMedicoRequest(
        @Length(min = 2, max = 150, message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @Length(min = 11, max = 11, message = "Telefone deve ter 11 números exatos")
        String telefone,
        @Positive(message = "Tempo de consulta é em minutos e não pode ser nagativo ou zero")
        @Min(value = 15, message = "Tempo de consulta deve ser no mínimo 15 minutos")
        @Max(value = 60, message = "Tempo de consulta de ser no máximo 60 minutos")
        Integer tempoConsultaMinutos,
        UUID salaAtendimentoId

) {
}
