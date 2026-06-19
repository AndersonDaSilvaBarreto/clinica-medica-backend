package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Você não possui permissão para realizar esta operação.");
    }
    public AcessoNegadoException(String message) {
        super(message);
    }
}
