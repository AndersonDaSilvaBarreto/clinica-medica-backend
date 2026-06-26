package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import java.time.Instant;
import java.util.UUID;

public record ReagendamentoResponse(
        UUID id,
        Instant dataHoraInicioAntiga,
        Instant dataHoraInicioNova,
        String motivo,
        String nomeOperador,
        Perfil perfilOperador,
        Instant dataAlteracao


) {
    public static ReagendamentoResponse fromEntity(ReagendamentoConsulta entity) {
        return new ReagendamentoResponse(
                entity.getId(),
                entity.getDataHoraInicioAntiga(),
                entity.getDataHoraInicioNova(),
                entity.getMotivo(),
                entity.getReagendadoPor().getNome().toString(),
                entity.getReagendadoPor().getPerfil(),
                entity.getDataCriacao()
        );
    }
}
