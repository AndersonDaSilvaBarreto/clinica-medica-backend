package com.topicos_especiais_1.clinica_medica.consulta.domain.repository;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ConsultaRepository {
    Consulta salvar(Consulta consulta);
    Consulta buscarPorId(UUID consultaId);
    boolean existeConflitoHorarioMedico(UUID medicoId, Instant inicio, Instant fim);
    List<Consulta> buscarPaginadaPorPacienteId(UUID pacienteId, UUID cursor, int limit); // [cite: 234]
    List<Consulta> buscarPaginada(UUID cursor,
                                  UUID pacienteId,
                                  UUID medicoId,
                                  StatusConsulta status,
                                  Instant dataInicio,
                                  Instant dataFim,
                                  int limit);
}
