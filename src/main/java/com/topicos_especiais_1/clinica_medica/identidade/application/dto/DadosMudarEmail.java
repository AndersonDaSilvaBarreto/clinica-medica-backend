package com.topicos_especiais_1.clinica_medica.identidade.application.dto;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.UUID;

public record DadosMudarEmail(
        UUID usuarioId,
        Email email,
        String codigo
) {
}
