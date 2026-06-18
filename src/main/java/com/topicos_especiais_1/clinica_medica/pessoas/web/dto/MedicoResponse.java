package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;

import java.util.List;
import java.util.UUID;

public record MedicoResponse(
        UUID id,
        String nome,
        String crm,
        List<String> especialidades,
        boolean ativo
) {
    public static MedicoResponse of(Medico medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getUsuario().getNome().toString(),
                medico.getCrm().toString(),
                medico.getEspecialidades()
                        .stream()
                        .map(e -> e.getNome().toString())
                        .toList(),
                medico.getUsuario().getAtivo()
        );
    }
}
