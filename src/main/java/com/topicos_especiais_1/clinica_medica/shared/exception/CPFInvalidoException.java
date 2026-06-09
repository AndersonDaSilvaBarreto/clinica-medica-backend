package com.topicos_especiais_1.clinica_medica.shared.exception;

public class CPFInvalidoException extends RuntimeException {
    public CPFInvalidoException(String message) {
        super(message);
    }
   public static CPFInvalidoException cpfInvalido() {
        return new CPFInvalidoException("CPF inválido");
   }

}
