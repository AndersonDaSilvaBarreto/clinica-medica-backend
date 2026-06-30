package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import org.springframework.data.jpa.domain.Specification;

public class MedicoSpecifications {
    public static Specification<Medico> porAtivoUsuario(Boolean ativo) {
        return (root, query, criteriaBuilder) ->
                ativo != null ? criteriaBuilder.equal(root.get("usuario").get("ativo"),ativo) : null;
    }
}
