package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException() {
        super("Token inválido");
    }
    private TokenInvalidoException(String motivo) {
        super("Token inválido: " + motivo);
    }
    public static TokenInvalidoException criacaoErro()
    {
        return new TokenInvalidoException("erro na criação do token");
    }

    public static TokenInvalidoException validacaoErro() {
        return new TokenInvalidoException("Erro na validação do token");
    }
}
