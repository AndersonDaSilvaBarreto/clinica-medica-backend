package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class RedisErroException extends RuntimeException {
    public RedisErroException(String message) {
        super(message);
    }
}
