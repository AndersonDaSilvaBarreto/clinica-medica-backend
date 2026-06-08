package com.topicos_especiais_1.clinica_medica.identidade.application.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;

public record DadosRefreshToken(
        UsuarioId usuarioId
) {
}
