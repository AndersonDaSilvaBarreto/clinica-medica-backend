package com.topicos_especiais_1.clinica_medica.notificacoes.infra.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;

public interface SpringDataNotificacaoRepository extends JpaRepository<Notificacao, UUID> {

    List<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(UUID usuarioId);

    List<Notificacao> findByUsuarioIdAndLidaFalseOrderByDataCriacaoDesc(UUID usuarioId);
}
