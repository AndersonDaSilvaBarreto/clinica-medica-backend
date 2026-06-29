package com.topicos_especiais_1.clinica_medica.atendimento.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AdicionarFilaRequest(
        @NotNull(message = "O ID da consulta é obrigatório.")
        UUID consultaId,

        @NotNull(message = "O ID da sala de atendimento é obrigatório.")
        UUID salaId
) {
}
