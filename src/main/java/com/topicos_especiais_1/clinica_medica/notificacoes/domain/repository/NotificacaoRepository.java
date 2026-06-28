package com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;

import java.util.List;
import java.util.UUID;

public interface NotificacaoRepository {
    Notificacao salvar(Notificacao notificacao);
    Notificacao buscarPorId(UUID notificacaoId);
    List<Notificacao> buscaPaginada(UUID cursor, UUID usuarioId, Boolean lida, int limit);
}
