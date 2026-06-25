package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class ConflitoException extends RuntimeException {
    public ConflitoException(String message) {
        super(message);
    }
    public static ConflitoException of(String entity, String message) {
        return new ConflitoException(entity + ": " + message);
    }
}
