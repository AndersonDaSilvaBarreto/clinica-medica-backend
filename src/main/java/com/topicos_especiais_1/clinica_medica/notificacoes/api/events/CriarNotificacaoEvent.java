package com.topicos_especiais_1.clinica_medica.notificacoes.api.events;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;

public record CriarNotificacaoEvent(
        Usuario usuario,
        String tipo,
        String mensagem
) {
}
