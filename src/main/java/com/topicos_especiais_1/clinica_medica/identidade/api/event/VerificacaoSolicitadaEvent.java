package com.topicos_especiais_1.clinica_medica.identidade.api.event;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

public record VerificacaoSolicitadaEvent(
        Email email,
        String codigo
) {
}
