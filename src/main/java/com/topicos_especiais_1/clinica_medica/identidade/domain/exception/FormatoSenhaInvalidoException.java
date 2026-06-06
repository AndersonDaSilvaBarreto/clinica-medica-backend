package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class FormatoSenhaInvalidoException extends RuntimeException {
    public static final String VAZIA = "não pode ser vazia";
    public static final String MUITO_CURTA = "deve ter no mínimo 8 caracteres";
    public static final String SEM_NUMERO = "deve conter ao menos um número";
    public static final String SEM_MAIUSCULA = "deve conter ao menos uma letra maiúscula";
    public static final String SEM_MINUSCULA = "deve conter ao menos uma letra minúscula";
    public static final String SEM_ESPECIAL = "deve conter ao menos um caracter especial";
    public FormatoSenhaInvalidoException(String motivo) {
        super("Senha inválida: " + motivo);
    }
}
