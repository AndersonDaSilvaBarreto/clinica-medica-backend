package com.topicos_especiais_1.clinica_medica.shared.domain.exception;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.UUID;

public class EntidadeExistenteException extends RuntimeException {
    public EntidadeExistenteException(String message) {
        super(message);
    }

    public static final String USUARIO = "Usuário";
    public static final String PACIENTE = "Paciente";
    public static final String CONVENIO = "Convênio";
    public static final String ESPECIALIDADE = "Especialidade";
    public static final String MEDICO = "Medico";
    public static final String RECEPCIONISTA = "Recepcionista";


    public static EntidadeExistenteException porId(String entidade, UUID id) {
        return new EntidadeExistenteException(
                entidade + " com o id " + id + " já existe"
        );
    }

    public static EntidadeExistenteException porEmail(String entidade, Email email) {
        return new EntidadeExistenteException(
                entidade + " com o email " + email + " já existe"
        );
    }

    public static EntidadeExistenteException porCampo(String entidade, String campo, Object valor) {
        return new EntidadeExistenteException(
                entidade + " com o " + campo + " " + valor + " já existe"
        );
    }
}
