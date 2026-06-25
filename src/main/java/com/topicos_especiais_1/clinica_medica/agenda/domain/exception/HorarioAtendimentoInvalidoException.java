package com.topicos_especiais_1.clinica_medica.agenda.domain.exception;

public class HorarioAtendimentoInvalidoException extends RuntimeException {
    public HorarioAtendimentoInvalidoException(String message) {
        super(message);
    }
    public static HorarioAtendimentoInvalidoException horarioInicioDepoisDeFim(){
        return new HorarioAtendimentoInvalidoException("Horario de Inicio não pode ser a frente do horario de fim");
    }
}
