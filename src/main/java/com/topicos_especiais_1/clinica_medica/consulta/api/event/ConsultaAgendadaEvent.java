package com.topicos_especiais_1.clinica_medica.consulta.api.event;

import java.time.Instant;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;


public record ConsultaAgendadaEvent(
        UUID consultaId,
        UUID pacienteUsuarioId,
        Email pacienteEmail,
        Instant dataHoraInicio
) {
}
