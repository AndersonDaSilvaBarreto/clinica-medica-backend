package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.enums.StatusHorarioMedico;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record HorarioMedicoResponse(
        UUID id,
        UUID medicoId,
        Instant dataHora,
        /** Representação legível em horário de Brasília, ex: "2025-07-15 07:20". */
        String dataHoraFormatada,
        StatusHorarioMedico status
) {
    private static final ZoneId FUSO          = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(FUSO);

    public static HorarioMedicoResponse de(HorarioMedico h) {
        return new HorarioMedicoResponse(
                h.getId(),
                h.getMedico().getId(),
                h.getDataHora(),
                FMT.format(h.getDataHora()),
                h.getStatus()
        );
    }
}
