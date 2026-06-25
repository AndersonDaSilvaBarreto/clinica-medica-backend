package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class BloqueioAgendaSpecifications {

    public static Specification<BloqueioAgenda> porMedicoId(UUID medicoId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("medico").get("id"), medicoId));
    }
    public static Specification<BloqueioAgenda> dataContidaNoBloqueio(LocalDate data) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("dataInicio"),data),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataFim"),data)
                ));
    }
}
