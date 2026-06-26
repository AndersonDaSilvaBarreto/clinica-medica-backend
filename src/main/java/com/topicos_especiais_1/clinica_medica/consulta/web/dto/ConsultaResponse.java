package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;

import java.time.Instant;
import java.util.UUID;

public record ConsultaResponse(
        UUID id,
        Instant dataHoraInicio,
        Instant dataHoraFim,
        StatusConsulta status,
        String observacao,
        PacienteResumoResponse paciente,
        MedicoResumoResponse medico
) {
    public record PacienteResumoResponse(
            UUID id,
            String nome,
            String email
    ) {}

    // Sub-record para resumir dados do médico na listagem/detalhes
    public record MedicoResumoResponse(
            UUID id,
            String nome,
            String crm
    ) {}

    public static ConsultaResponse fromEntity(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getDataHoraInicio(),
                consulta.getDataHoraFim(),
                consulta.getStatusConsulta(),
                consulta.getObservacao(),
                new PacienteResumoResponse(
                        consulta.getPaciente().getId(),
                        consulta.getPaciente().getUsuario().getNome().toString(),
                        consulta.getPaciente().getUsuario().getEmail().toString()
                ),
                new MedicoResumoResponse(
                        consulta.getMedico().getId(),
                        consulta.getMedico().getUsuario().getNome().toString(),
                        consulta.getMedico().getCrm().toString()
                )

        );
    }
}
