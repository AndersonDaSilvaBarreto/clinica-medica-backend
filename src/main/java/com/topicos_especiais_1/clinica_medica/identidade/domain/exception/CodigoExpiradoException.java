package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class CodigoExpiradoException extends RuntimeException {
    public static final String CODIGO_EXPIRADO = "codigo expirou";
    public static final String REFRESH_TOKEN_EXPIRADO = "refresh token expirou";
    public CodigoExpiradoException(String motivo) {
        super("Código expirado: " + motivo);
    }
}
