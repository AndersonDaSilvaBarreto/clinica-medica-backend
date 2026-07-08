package com.topicos_especiais_1.clinica_medica.shared.web.dto;

public record MensagemResponse(String message) {
    public static MensagemResponse of(String message) {
        return new MensagemResponse(message);
    }
}
