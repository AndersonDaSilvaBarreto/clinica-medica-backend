package com.topicos_especiais_1.clinica_medica.shared.api;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Builder
public record ErroResponse(
       String type,
       String title,
       int status,
       String instance,
       Instant timestamp,
       List<ErroDetalhe> errors
) {
    public record ErroDetalhe(String field, String message) {}

    public static ErroResponse of(String title, HttpStatus status, String instance) {
        return ErroResponse.builder()
                .type("https://sumed.online/errors/" + status.name().toLowerCase())
                .title(title)
                .status(status.value())
                .instance(instance)
                .timestamp(Instant.now())
                .build();
    }

    // erro de validação com múltiplos campos
    public static ErroResponse ofValidacao(String instance, List<ErroDetalhe> errors) {
        return ErroResponse.builder()
                .type("https://sumed.online/errors/validacao")
                .title("Erro de validação")
                .status(400)
                .instance(instance)
                .timestamp(Instant.now())
                .errors(errors)
                .build();
    }
}
