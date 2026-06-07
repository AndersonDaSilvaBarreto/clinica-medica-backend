package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException() {
        super("Usuário existente");
    }
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
