package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class DescricaoInvalidaException extends RuntimeException {
    public DescricaoInvalidaException(String message) {
        super(message);
    }
    public static DescricaoInvalidaException descricaoInvalida() {
        return new DescricaoInvalidaException("Descrição inválida: a descrição deve ter entre 15 e 500 caracteres");
    }
}
