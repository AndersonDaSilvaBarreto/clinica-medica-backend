package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record MedicoResponse(
        UUID id,
        String nome,
        String crm,
        List<EspecialidadeResumidaResponse> especialidades,
        boolean ativo,
        Instant dataCriacao,
        SalaAtendimentoResumidaResponse salaAtendimento
) {
    public static MedicoResponse of(Medico medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getUsuario().getNome().toString(),
                medico.getCrm().toString(),
                medico.getEspecialidades()
                        .stream()
                        .map(e -> new EspecialidadeResumidaResponse(
                                e.getId(),
                                e.getNome().toString()
                        ))
                        .toList(),
                medico.getUsuario().getAtivo(),
                medico.getDataCriacao(),
                medico.getSalaAtendimento() != null
                        ? new SalaAtendimentoResumidaResponse(
                                medico.getSalaAtendimento().getId(),
                                medico.getSalaAtendimento().getNome().toString()
                          )
                        : null
        );
    }
    public record EspecialidadeResumidaResponse(
            UUID id,
            String nome
    ) {}
    public record SalaAtendimentoResumidaResponse(
            UUID id,
            String nome
    ) {}
}
