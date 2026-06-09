package com.topicos_especiais_1.clinica_medica.shared.exception;

public class FormatoTelefoneInvalidoException extends RuntimeException {
    public static final String VAZIO = "não pode ser vazio";
    public static final String INVALIDO = "formato inválido";
    public FormatoTelefoneInvalidoException(String motivo) {
        super("Telefone invalido: " + motivo);
    }
}
