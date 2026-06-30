package com.topicos_especiais_1.clinica_medica.dashboard.web.dto;

public record CardsResponse(
        long totalConsultas,
        long medicosAtivos,
        long pacienteCadastrados,
        double taxaComparecimento
) {
}
