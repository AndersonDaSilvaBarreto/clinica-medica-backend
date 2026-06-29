package com.topicos_especiais_1.clinica_medica.notificacoes.application.mapper;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.web.dto.NotificacaoResponse;

public class NotificacaoMapper {

    private NotificacaoMapper() {}

    public static NotificacaoResponse toResponse(Notificacao notificacao) {
        return new NotificacaoResponse(
                notificacao.getId(),
                notificacao.getTipo().name(),
                notificacao.getMensagem(),
                notificacao.isLida(),
                notificacao.getDataCriacao()
        );
    }
}
