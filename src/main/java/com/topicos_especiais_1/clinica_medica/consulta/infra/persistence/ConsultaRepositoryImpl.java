package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsultaRepositoryImpl implements ConsultaRepository {

    private final SpringDataConsulta springDataConsulta;

    @Override
    public Consulta salvar(Consulta consulta) {
        return springDataConsulta.save(consulta);
    }

    @Override
    public Consulta buscarPorId(UUID consultaId) {
        return springDataConsulta.findById(consultaId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        "Consulta",
                        consultaId
                )
        );
    }

    @Override
    public boolean existeConflitoHorarioMedico(UUID medicoId, Instant inicio, Instant fim) {
        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.porMedicoId(medicoId))
                .and(ConsultaSpecifications.statusDiferenteDe(List.of(StatusConsulta.CANCELADA, StatusConsulta.REAGENDADA)))
                .and(ConsultaSpecifications.sobrepoeHorario(inicio, fim));
        return springDataConsulta.exists(specs);
    }

    @Override
    public List<Consulta> buscarPaginadaPorPacienteId(UUID pacienteId, UUID cursor, int limit) {
        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.porPacienteId(pacienteId))
                .and(ConsultaSpecifications.idMaiorQue(cursor));
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "id"));
        return springDataConsulta.findAll(specs, pageRequest).getContent();
    }

    @Override
    public List<Consulta> buscarPaginada(UUID cursor, UUID pacienteId, UUID medicoId, StatusConsulta status, Instant dataInicio, Instant dataFim, int limit) {
        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.porPacienteId(pacienteId))
                .and(ConsultaSpecifications.porMedicoId(medicoId))
                .and((ConsultaSpecifications.porStatus(status)))
                .and(ConsultaSpecifications.dataHoraInicioDepoisDe(dataInicio))
                .and(ConsultaSpecifications.dataHoraInicioAntesDe(dataFim))
                .and(ConsultaSpecifications.idMaiorQue(cursor));
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "id"));
        return springDataConsulta.findAll(specs, pageable).getContent();
    }

    @Override
    public List<Consulta> buscarConsultasAtivasPorMedicoEData(UUID medicoId, Instant inicioDia, Instant fimDia) {
        Specification<Consulta> specs = Specification
                .where(ConsultaSpecifications.porMedicoId(medicoId))
                .and(ConsultaSpecifications.statusDiferenteDe(
                        List.of(StatusConsulta.CANCELADA, StatusConsulta.REAGENDADA)))
                .and(ConsultaSpecifications.dataHoraInicioDepoisDe(inicioDia))
                .and(ConsultaSpecifications.dataHoraInicioAntesDe(fimDia));
        return springDataConsulta.findAll(specs, Sort.by(Sort.Direction.ASC, "dataHoraInicio"));
    }

    @Override
    public boolean existeConflitoHorarioMedicoIgnorandoConsulta(UUID medicoId, Instant inicio, Instant fim, UUID consultaId) {
        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.porMedicoId(medicoId))
                .and(ConsultaSpecifications.sobrepoeHorario(inicio, fim))
                .and(ConsultaSpecifications.idDiferenteDe(consultaId));
        return springDataConsulta.exists(specs);
    }

    @Override
    public List<Consulta> buscarConsultasParaMarcarFaltou(Instant limite) {
        Specification<Consulta> specs = Specification
                .where(ConsultaSpecifications.statusEm(
                        List.of(StatusConsulta.AGENDADA, StatusConsulta.REAGENDADA)))
                .and(ConsultaSpecifications.dataHoraInicioAntesDe(limite));
        return springDataConsulta.findAll(specs, Sort.by(Sort.Direction.ASC, "dataHoraInicio"));
    }
}
