package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class FormatoInvalidoException extends RuntimeException {
    public FormatoInvalidoException(String message) {
        super(message);
    }
    public static FormatoInvalidoException from(String entity, String message) {
        return new FormatoInvalidoException(entity + ": " + message);
    }
}
