package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.UUID;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
    public static final String USUARIO = "Usuário";
    public static final String PACIENTE = "Paciente";
    public static final String ESPECIALIDADE = "Especialidade";
    public static final String MEDICO = "Medico";
    public static final String MEDICO_ESPECIALIDADE = "Medico Especialidade";
    public static final String RECEPCIONISTA = "Recepcionista";
    public static final String SALA_ATENDIMENTO = "Sala Atendimento";

    public static EntidadeNaoEncontradaException porId(String entidade, UUID id) {
        return new EntidadeNaoEncontradaException(
                entidade + " com o id " + id + " não encontrado"
        );
    }

    public static EntidadeNaoEncontradaException porEmail(String entidade, Email email) {
        return new EntidadeNaoEncontradaException(
                entidade + " com o email " + email + " não encontrado"
        );
    }

    public static EntidadeNaoEncontradaException porCampo(String entidade, String campo, Object valor) {
        return new EntidadeNaoEncontradaException(
                entidade + " com o " + campo + " " + valor + " não encontrado"
        );
    }
}
