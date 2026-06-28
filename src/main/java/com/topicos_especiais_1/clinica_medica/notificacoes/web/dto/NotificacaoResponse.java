package com.topicos_especiais_1.clinica_medica.notificacoes.web.dto;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;

import java.time.Instant;
import java.util.UUID;

public record NotificacaoResponse(
        UUID id,
        String tipo,
        String mensagem,
        Boolean lida,
        Instant dataCriacao
) {
    public static NotificacaoResponse fromEntity(Notificacao notificacao) {
        return new NotificacaoResponse(
                notificacao.getId(),
                notificacao.getTipo(),
                notificacao.getMensagem(),
                notificacao.getLida(),
                notificacao.getDataCriacao() // Assumindo que herda do seu BaseEntity
        );
    }
}
