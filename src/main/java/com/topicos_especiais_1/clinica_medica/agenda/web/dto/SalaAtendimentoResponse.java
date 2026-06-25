package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;

import java.util.UUID;

public record SalaAtendimentoResponse(
        UUID id,
        String nome,
        String descricao,
        Boolean ativa
) {
    public static SalaAtendimentoResponse from(SalaAtendimento salaAtendimento) {
        return new SalaAtendimentoResponse(
                salaAtendimento.getId(),
                salaAtendimento.getNome().toString(),
                salaAtendimento.getDescricao().map(Descricao::toString).orElse(null),
                salaAtendimento.getAtiva()
        );
    }
}
