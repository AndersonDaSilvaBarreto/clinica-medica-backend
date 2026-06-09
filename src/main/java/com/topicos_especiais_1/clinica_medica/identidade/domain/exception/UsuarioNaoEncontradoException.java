package com.topicos_especiais_1.clinica_medica.identidade.domain.exception;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado");
    }

    private UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public static UsuarioNaoEncontradoException porId(UsuarioId usuarioId) {
        return new UsuarioNaoEncontradoException(
                "Usuário com o id " + usuarioId.toString() + " não encontrado");
    }
    public static UsuarioNaoEncontradoException porEmail(Email email) {
        return new UsuarioNaoEncontradoException(
                "Usuário com o email " + email.toString() + " não encontrado");
    }
}
