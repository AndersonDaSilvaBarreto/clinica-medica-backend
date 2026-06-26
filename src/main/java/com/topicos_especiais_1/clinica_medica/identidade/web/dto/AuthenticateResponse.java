package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

public record AuthenticateResponse(
        String accessToken,
        String refreshToken,
        String role
) {
}
