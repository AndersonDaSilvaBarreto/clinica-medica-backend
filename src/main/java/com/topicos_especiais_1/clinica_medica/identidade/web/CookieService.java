package com.topicos_especiais_1.clinica_medica.identidade.web;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.AccessCookieAndRefreshCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CookieService {
    @Value("${app.cookie.domain}")
    private String cookieDomain;
    public AccessCookieAndRefreshCookie gerarCookiesAutenticacao(
            String accessToken,
            String refreshToken) {
        ResponseCookie.ResponseCookieBuilder accessCookie = ResponseCookie.from(
                "accessToken", accessToken)
                .httpOnly(true)
                .secure(cookieDomain != null)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("None");

        ResponseCookie.ResponseCookieBuilder refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(cookieDomain != null)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("None");
        if (cookieDomain != null && !cookieDomain.isBlank()) {
            accessCookie.domain(cookieDomain);
            refreshCookie.domain(cookieDomain);
        }
        return new AccessCookieAndRefreshCookie(
                accessCookie.build(),
                refreshCookie.build()
        );

    }
}
