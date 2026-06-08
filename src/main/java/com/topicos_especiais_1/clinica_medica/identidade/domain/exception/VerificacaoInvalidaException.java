package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class VerificacaoInvalidaException extends RuntimeException {
    public static final String CODIGO_INVALIDO = "código inválido";
    public VerificacaoInvalidaException(String motivo) {
        super("Verificacao inválida: " + motivo);
    }
}
