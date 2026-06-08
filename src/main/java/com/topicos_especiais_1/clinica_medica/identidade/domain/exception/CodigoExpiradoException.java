package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class CodigoExpiradoException extends RuntimeException {
    public static final String CODIGO_EXPIRADO = "codigo expirou";
    public CodigoExpiradoException(String motivo) {
        super("Código expirado: " + motivo);
    }
}
