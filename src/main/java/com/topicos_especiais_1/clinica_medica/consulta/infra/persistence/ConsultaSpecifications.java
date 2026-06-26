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
                medicoId != null ?cb.equal(root.get("medico").get("id"),medicoId) : null);
    }
    public static Specification<Consulta> porPacienteId(UUID pacienteId) {
        return (root, _, cb) ->
                pacienteId != null? cb.equal(root.get("paciente").get("id"), pacienteId) : null;
    }

    public static Specification<Consulta> statusDiferenteDe(List<StatusConsulta> status) {
        return (root, _, cb) -> cb.not(root.get("statusConsulta").in(status));
    }
    public static Specification<Consulta> sobrepoeHorario(Instant inicio, Instant fim) {
        return ((root, _, cb) ->
                cb.or(
                        cb.and(cb.greaterThanOrEqualTo(root.get("dataHoraInicio"),inicio), cb.lessThan(root.get("dataHoraInicio"),fim)),
                        cb.and(cb.greaterThan(root.get("dataHoraFim"),inicio),cb.lessThanOrEqualTo(root.get("dataHoraFim"),fim)),
                        cb.and(cb.lessThanOrEqualTo(root.get("dataHoraInicio"),inicio), cb.greaterThanOrEqualTo(root.get("dataHoraFim"),fim))
                ));
    }
    public static Specification<Consulta> idMaiorQue(UUID cursor) {
        return (root, _, cb) -> cursor == null? null : cb.greaterThan(root.get("id"),cursor);
    }
    public static Specification<Consulta> porStatus(StatusConsulta statusConsulta) {
        return (root, _, criteriaBuilder) ->
                statusConsulta != null? criteriaBuilder.equal(root.get("statusConsulta"),statusConsulta) : null;
    }
    public static Specification<Consulta> dataHoraInicioDepoisDe(Instant inicio) {
        return ((root, _, cb) ->
                inicio != null ? cb.greaterThanOrEqualTo(root.get("dataHoraInicio"), inicio): null);
    }
    public static Specification<Consulta> dataHoraInicioAntesDe(Instant fim) {
        return (root, _, cb) ->
                fim != null ? cb.lessThanOrEqualTo(root.get("dataHoraInicio"), fim) : null;
    }

}
