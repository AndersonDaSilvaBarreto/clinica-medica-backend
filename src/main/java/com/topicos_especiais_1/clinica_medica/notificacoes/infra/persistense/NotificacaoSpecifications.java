package com.topicos_especiais_1.clinica_medica.notificacoes.infra.persistence;

import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class NotificacaoSpecifications {

    public static Specification<Notificacao> porUsuarioId(UUID usuarioId) {
        return (root, _, criteriaBuilder) ->
             usuarioId != null ? criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId) : null;
    }
    public static Specification<Notificacao> porLida(Boolean lida) {
        return (root, query, criteriaBuilder) ->
            lida != null ? criteriaBuilder.equal(root.get("lida"),lida) : null;
    }
    public static Specification<Notificacao> idMenorQue(UUID id) {
        return (root, query, criteriaBuilder) ->
                id != null ? criteriaBuilder.lessThan(root.get("id"),id) : null;
    }
}
