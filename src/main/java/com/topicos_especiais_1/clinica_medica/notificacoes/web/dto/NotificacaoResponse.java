package com.topicos_especiais_1.clinica_medica.notificacoes.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;

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
                notificacao.getTipo().name(),
                notificacao.getMensagem(),
                notificacao.isLida(),
                notificacao.getDataCriacao() 
        );
    }
}
