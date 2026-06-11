package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class FormatoEmailInvalidoException extends RuntimeException {
    public static final String VAZIO = "não pode ser vazio";
    public static final String INVALIDO = "formato invalido";
    public FormatoEmailInvalidoException(String motivo) {
        super("Email inválido: " + motivo);
    }
}
