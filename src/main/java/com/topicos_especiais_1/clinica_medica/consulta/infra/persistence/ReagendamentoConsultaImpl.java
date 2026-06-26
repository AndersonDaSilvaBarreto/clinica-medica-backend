package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ReagendamentoConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReagendamentoConsultaImpl implements ReagendamentoConsultaRepository {
    private final SpringDataReagendamento springDataReagendamento;
    @Override
    public ReagendamentoConsulta salvar(ReagendamentoConsulta reagendamentoConsulta) {
        return springDataReagendamento.save(reagendamentoConsulta);
    }

    @Override
    public List<ReagendamentoConsulta> buscaPaginada(
            UUID cursor,
            UUID consultaId,
            UUID pacienteId,
            Instant depoisDe,
            Instant antesDe,
            int limit) {
        Specification<ReagendamentoConsulta> specs = Specification.
                where(ReagendamentoConsultaSpecifications.porConsultaId(consultaId))
                .and(ReagendamentoConsultaSpecifications.porPacienteId(pacienteId))
                .and(ReagendamentoConsultaSpecifications.porDataCriacaoDepoisDe(depoisDe))
                .and(ReagendamentoConsultaSpecifications.porDataCriacaoAntesDe(antesDe))
                .and(ReagendamentoConsultaSpecifications.idMaiorQue(cursor));
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "id"));
        return springDataReagendamento.findAll(specs,pageable).getContent();
    }
}
