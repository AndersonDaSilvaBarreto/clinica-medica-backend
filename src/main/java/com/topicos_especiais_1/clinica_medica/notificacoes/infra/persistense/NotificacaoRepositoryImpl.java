package com.topicos_especiais_1.clinica_medica.notificacoes.infra.persistense;


import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificacaoRepositoryImpl implements NotificacaoRepository {
    private final SpringDataNotificacao springDataNotificacao;


    @Override
    public Notificacao salvar(Notificacao notificacao) {
        return springDataNotificacao.save(notificacao);
    }

    @Override
    public Notificacao buscarPorId(UUID notificacaoId) {
        return springDataNotificacao.findById(notificacaoId).orElseThrow(() ->
                EntidadeNaoEncontradaException.porId("Notificação", notificacaoId));
    }

    @Override
    public List<Notificacao> buscarPorUsuarioId(UUID usuarioId) {
        return springDataNotificacao.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
    }

    @Override
    public List<Notificacao> buscarNaoLidasPorUsuarioId(UUID usuarioId) {
        return springDataNotificacao.findByUsuarioIdAndLidaFalseOrderByDataCriacaoDesc(usuarioId);
    }

    @Override
    public List<Notificacao> buscaPaginada(UUID cursor, UUID usuarioId, Boolean lida, int limit) {
        Specification<Notificacao> specs = Specification.where(NotificacaoSpecifications.porUsuarioId(usuarioId))
                .and(NotificacaoSpecifications.porLida(lida))
                .and(NotificacaoSpecifications.idMenorQue(cursor));
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC,"id"));

        return springDataNotificacao.findAll(specs,pageable).getContent();

    }
}
