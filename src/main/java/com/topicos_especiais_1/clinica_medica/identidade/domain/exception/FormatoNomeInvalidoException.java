package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class FormatoNomeInvalidoException extends RuntimeException {
    public static final String VAZIO = "não pode ser vazio";
    public static final String TAMANHO = "deve ter entre 2 e 150 caracteres";
    public FormatoNomeInvalidoException(String motivo) {
        super("Nome inválido: " + motivo);
    }
}
