package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;

import java.math.BigDecimal;
import java.util.UUID;

public record EspecialidadeResponse(
        UUID id,
        String nome,
        String descricao,
        BigDecimal valorConsulta
) {
    public static EspecialidadeResponse ofEspecialidade(Especialidade especialidade) {
        return new EspecialidadeResponse(
                especialidade.getId(),
                especialidade.getNome().toString(),
                especialidade.getDescricao().map(Descricao::toString).orElse(null),
                especialidade.getValorConsulta().getValue()
        );
    }
}
