package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CriarBloqueioAgendaRequest(
        UUID medicoId,
        LocalDate dataInicio,
        LocalDate dataFim,
        String motivo
) {
}
