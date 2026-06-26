package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;

import java.time.Instant;
import java.util.UUID;

public record ProntuarioResponse(
        UUID id,
        UUID consultaId,
        UUID pacienteId,
        String nomePaciente,
        UUID medicoId,
        String nomeMedico,
        String historico,
        String receita,
        String examesSolicitados,
        Instant dataCriacao
) {
    public static ProntuarioResponse fromEntity(Prontuario prontuario) {
        return new ProntuarioResponse(
                prontuario.getId(),
                prontuario.getConsulta().getId(),
                prontuario.getPaciente().getId(),
                prontuario.getPaciente().getUsuario().getNome().toString(),
                prontuario.getMedico().getId(),
                prontuario.getMedico().getUsuario().getNome().toString(),
                prontuario.getHistorico(),
                prontuario.getReceita(),
                prontuario.getExamesSolicitados(),
                prontuario.getDataCriacao()

        );
    }
}
