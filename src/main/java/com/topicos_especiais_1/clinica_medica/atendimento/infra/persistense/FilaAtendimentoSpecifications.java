package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class FilaAtendimentoSpecifications {

    public static Specification<FilaAtendimento> porMedicoId(UUID medicoId) {
        return (root, query, criteriaBuilder) ->
                medicoId != null? criteriaBuilder.equal(root.get("medico").get("id"),medicoId):null;
    }
    public static Specification<FilaAtendimento> idMaiorQue(UUID id) {
        return (root, query, criteriaBuilder) ->
                id != null ? criteriaBuilder.greaterThan(root.get("id"),id) : null;
    }
    public static Specification<FilaAtendimento> idMenorQue(UUID id) {
        return (root, query, criteriaBuilder) ->
                id != null ? criteriaBuilder.lessThan(root.get("id"),id) : null;
    }
    public static Specification<FilaAtendimento> ordemFilaMaiorQue(Integer ordemFila) {
        return (root, query, criteriaBuilder) ->
                ordemFila != null ? criteriaBuilder.greaterThan(root.get("ordemFila"),ordemFila) : null;
    }
    public static Specification<FilaAtendimento> porStatusIgualA(StatusFila statusFila) {
        return (root, query, criteriaBuilder) ->
                statusFila != null ? criteriaBuilder.equal(root.get("status"),statusFila) : null;
    }
    public static Specification<FilaAtendimento> porDataDia(LocalDate dataFila) {
        return (root, query, criteriaBuilder) ->
                dataFila != null ? criteriaBuilder.equal(root.get("dataFila"),dataFila) : null;
    }
    public static Specification<FilaAtendimento> porConsultaId(UUID consultaId) {
        return (root, query, criteriaBuilder) ->
                consultaId != null ? criteriaBuilder.equal(root.get("consulta").get("id"), consultaId) : null;
    }

}
