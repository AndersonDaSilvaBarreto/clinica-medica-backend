package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }
    public static ValorInvalidoException valorNegativo() {
        return new ValorInvalidoException("Valor não pode ser negativo");
    }
}
