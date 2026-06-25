package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ConsultaSpecifications {
    public static Specification<Consulta> porMedicoId(UUID medicoId) {
        return ((root, _, cb) ->
                cb.equal(root.get("medico").get("id"),medicoId));
    }
    public static Specification<Consulta> porPacienteId(UUID pacienteId) {
        return (root, query, cb) -> cb.equal(root.get("paciente").get("id"), pacienteId);
    }

    public static Specification<Consulta> statusDiferenteDe(List<StatusConsulta> status) {
        return (root, query, cb) -> cb.not(root.get("statusConsulta").in(status));
    }
    public static Specification<Consulta> sobrepoeHorario(Instant inicio, Instant fim) {
        return ((root, query, cb) ->
                cb.or(
                        cb.and(cb.greaterThanOrEqualTo(root.get("dataHoraInicio"),inicio), cb.lessThan(root.get("dataHoraInicio"),fim)),
                        cb.and(cb.greaterThan(root.get("dataHoraFim"),inicio),cb.lessThanOrEqualTo(root.get("dataHoraFim"),fim)),
                        cb.and(cb.lessThanOrEqualTo(root.get("dataHoraInicio"),inicio), cb.greaterThanOrEqualTo(root.get("dataHoraFim"),fim))
                ));
    }
    public static Specification<Consulta> idMaiorQue(UUID cursor) {
        return (root,query,cb) -> cursor == null? null : cb.greaterThan(root.get("id"),cursor);
    }
}
