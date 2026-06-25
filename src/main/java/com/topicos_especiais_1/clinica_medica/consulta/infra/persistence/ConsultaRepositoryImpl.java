package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
                .and(ConsultaSpecifications.sobrepoeHorario(inicio,fim));
        return springDataConsulta.exists(specs);
    }

    @Override
    public List<Consulta> buscarPaginadaPorPacienteId(UUID pacienteId, UUID cursor, int limit) {
        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.porPacienteId(pacienteId))
                .and(ConsultaSpecifications.idMaiorQue(cursor));
        PageRequest pageRequest = PageRequest.of(0,limit, Sort.by(Sort.Direction.ASC,"id"));
        return springDataConsulta.findAll(specs, pageRequest).getContent();
    }

    @Override
    public List<Consulta> buscarPaginada(UUID cursor, UUID pacienteId, UUID medicoId, StatusConsulta status, Instant dataInicio, Instant dataFim, int limit) {
        return List.of();
    }
}
