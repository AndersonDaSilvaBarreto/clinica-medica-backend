package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;

import jakarta.validation.constraints.NotNull;

public record AtualizarStatusConsultaRequest(
        @NotNull(message = "O status é obrigatório.")
        StatusConsulta status
) {}
