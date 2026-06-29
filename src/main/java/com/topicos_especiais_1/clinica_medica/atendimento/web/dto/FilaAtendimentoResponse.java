package com.topicos_especiais_1.clinica_medica.atendimento.web.dto;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record FilaAtendimentoResponse(
        UUID id,
        UUID consultaId,
        UUID medicoId,
        String medicoNome,
        UUID pacienteId,
        String pacienteNome,
        UUID salaId,
        String salaNome,
        Integer ordemFila,
        LocalDate dataFila,
        Instant horarioChamada,
        StatusFila status
) {
    public static FilaAtendimentoResponse fromEntity(FilaAtendimento fila) {
        return new FilaAtendimentoResponse(
                fila.getId(),
                fila.getConsulta().getId(),
                fila.getMedico().getId(),
                fila.getMedico().getUsuario().getNome().toString(),
                fila.getPaciente().getId(),
                fila.getPaciente().getUsuario().getNome().toString(),
                fila.getSala().getId(),
                fila.getSala().getNome().toString(),
                fila.getOrdemFila(),
                fila.getDataFila(),
                fila.getHorarioChamada(),
                fila.getStatus()
        );
    }
}
