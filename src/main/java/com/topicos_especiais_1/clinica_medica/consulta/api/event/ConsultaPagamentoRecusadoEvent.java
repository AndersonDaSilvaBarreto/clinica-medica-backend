package com.topicos_especiais_1.clinica_medica.consulta.api.event;

import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;


public record ConsultaPagamentoRecusadoEvent(
        UUID consultaId,
        UUID pacienteUsuarioId,
        Email pacienteEmail,
        String motivo
) {
}
