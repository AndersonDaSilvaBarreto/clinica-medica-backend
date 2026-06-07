package com.topicos_especiais_1.clinica_medica.identidade.api.event;

public record VerificacaoSolicitadaEvent(
        String email,
        String codigo
) {
}
