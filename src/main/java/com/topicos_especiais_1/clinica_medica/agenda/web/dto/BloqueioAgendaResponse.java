package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;

import java.time.LocalDate;
import java.util.UUID;

public record BloqueioAgendaResponse(
        UUID id,
        UUID medicoId,
        LocalDate dataInicio,
        LocalDate dataFim,
        String motivo
) {
    public static BloqueioAgendaResponse of(BloqueioAgenda bloqueioAgenda) {
            return new BloqueioAgendaResponse(
                    bloqueioAgenda.getId(),
                    bloqueioAgenda.getMedico().getId(),
                    bloqueioAgenda.getDataInicio(),
                    bloqueioAgenda.getDataFim(),
                    bloqueioAgenda.getMotivo().orElse(null)
            );
    }
}
