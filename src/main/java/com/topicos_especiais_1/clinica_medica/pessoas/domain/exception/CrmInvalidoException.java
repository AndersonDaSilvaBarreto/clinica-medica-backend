package com.topicos_especiais_1.clinica_medica.pessoas.domain.exception;

public class CrmInvalidoException extends RuntimeException {
    public CrmInvalidoException(String message) {
        super(message);
    }
    public static CrmInvalidoException crmInvalido() {
        return new CrmInvalidoException("Crm inválido");
    }
}
