package com.topicos_especiais_1.clinica_medica.notificacoes.infra.persistence;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataNotificacao extends JpaRepository<Notificacao, UUID>, JpaSpecificationExecutor<Notificacao> {
    @Override
    @EntityGraph(attributePaths = "usuario")
    Optional<Notificacao> findById(@NonNull UUID id);

    List<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(UUID usuarioId);

    List<Notificacao> findByUsuarioIdAndLidaFalseOrderByDataCriacaoDesc(UUID usuarioId);
}
