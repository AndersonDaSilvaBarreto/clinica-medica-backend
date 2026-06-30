package com.topicos_especiais_1.clinica_medica.dashboard.web.dto;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

public record ProximasConsultasResponse(
        UUID consultaId,
        String nomePaciente,
        String cpfPaciente,
        LocalTime horario,
        String medico,
        String especialidade,
        StatusConsulta statusConsulta
) {
    private static final ZoneId FUSO = ZoneId.of("America/Sao_Paulo");

    public static ProximasConsultasResponse fromEntity(Consulta consulta) {
        // Extrai o LocalTime puro baseado no fuso horário correto
        LocalTime horarioLocal = consulta.getDataHoraInicio()
                .atZone(FUSO)
                .toLocalTime();

        return new ProximasConsultasResponse(
                consulta.getId(),
                consulta.getPaciente().getUsuario().getNome().toString(),
                consulta.getPaciente().getUsuario().getCpf().toString(),
                horarioLocal,
                consulta.getMedico().getUsuario().getNome().toString(),
                consulta.getEspecialidade().getNome().toString(),
                consulta.getStatusConsulta()
        );
    }
}
