package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.ConfirmacaoPresenca;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ConfirmacaoPresencaSpecifications {

    public static Specification<ConfirmacaoPresenca> porConsultaId(UUID consultaId) {
        return (root, _, criteriaBuilder) ->
                consultaId != null ? criteriaBuilder.equal(root.get("consulta").get("id"),consultaId) : null;
    }
}
