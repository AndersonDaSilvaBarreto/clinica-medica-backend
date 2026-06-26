package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public class ReagendamentoConsultaSpecifications {

    public static Specification<ReagendamentoConsulta> porPacienteId(UUID pacienteId) {
        return (root, query, cb) ->
                pacienteId != null? cb.equal(root.get("reagendadoPor").get("id"), pacienteId) : null;
    }

    public static Specification<ReagendamentoConsulta> porConsultaId(UUID consultaId) {
        return (root, query, criteriaBuilder) ->
                consultaId != null? criteriaBuilder.equal(root.get("consulta").get("id"),consultaId): null;
    }

    public static Specification<ReagendamentoConsulta> idMaiorQue(UUID cursor) {
        return ((root, _, cb) ->
                cursor != null ? cb.greaterThan(root.get("id"),cursor): null);
    }
    public static Specification<ReagendamentoConsulta> porDataCriacaoDepoisDe(Instant inicio) {
        return (root, query, cb) ->
                inicio != null? cb.greaterThanOrEqualTo(root.get("dataCriacao"),inicio) : null;
    }
    public static Specification<ReagendamentoConsulta> porDataCriacaoAntesDe(Instant fim) {
        return (root, query, cb) ->
                fim != null? cb.lessThanOrEqualTo(root.get("dataCriacao"),fim) : null;
    }
}
