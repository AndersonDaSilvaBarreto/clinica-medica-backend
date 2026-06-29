package com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository;

import java.util.List;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;

public interface NotificacaoRepository {

    Notificacao salvar(Notificacao notificacao);

    Notificacao buscarPorId(UUID notificacaoId);

    List<Notificacao> buscarPorUsuarioId(UUID usuarioId);

    List<Notificacao> buscarNaoLidasPorUsuarioId(UUID usuarioId);

    List<Notificacao> buscaPaginada(UUID cursor, UUID usuarioId, Boolean lida, int limit);
}
