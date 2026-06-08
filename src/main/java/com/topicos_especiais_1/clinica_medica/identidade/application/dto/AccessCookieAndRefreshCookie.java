package com.topicos_especiais_1.clinica_medica.identidade.application.dto;

import org.springframework.http.ResponseCookie;

public record AccessCookieAndRefreshCookie(
        ResponseCookie accessCookie,
        ResponseCookie refreshCookie
) {
}
