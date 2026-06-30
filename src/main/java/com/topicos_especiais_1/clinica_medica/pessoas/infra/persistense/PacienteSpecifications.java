package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class PacienteSpecifications {
    public static Specification<Paciente> porDataCriacaoDepoisDe(Instant inicio) {
        return (root, query, criteriaBuilder) ->
                inicio != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"),inicio) : null;
    }
    public static Specification<Paciente> porDataCriacaoAntesDe(Instant fim) {
        return (root, query, criteriaBuilder) ->
                fim != null? criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"),fim) : null;
    }
}
