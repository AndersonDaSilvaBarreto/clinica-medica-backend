package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

public class DataNascimentoInvalidaException extends RuntimeException {
    public DataNascimentoInvalidaException(String message) {
        super(message);
    }
    public static DataNascimentoInvalidaException dataInvalida() {
        return new DataNascimentoInvalidaException("Data de nascimento inválida: precisa ser no passado");
    }
}
