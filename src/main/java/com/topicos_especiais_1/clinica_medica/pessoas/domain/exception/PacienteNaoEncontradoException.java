package com.topicos_especiais_1.clinica_medica.pessoas.domain.exception;

import java.util.UUID;

public class PacienteNaoEncontradoException extends RuntimeException {
    public PacienteNaoEncontradoException(String message) {
        super(message);
    }

    public static PacienteNaoEncontradoException porId(UUID id) {
        return new PacienteNaoEncontradoException(
                "Usuário com o id " + id + " não encontrado"
        );
    }
}
