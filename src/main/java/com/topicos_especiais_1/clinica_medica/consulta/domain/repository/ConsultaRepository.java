package com.topicos_especiais_1.clinica_medica.consulta.domain.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;

public interface ConsultaRepository {
    Consulta salvar(Consulta consulta);
    Consulta buscarPorId(UUID consultaId);
    boolean existeConflitoHorarioMedico(UUID medicoId, Instant inicio, Instant fim);
    List<Consulta> buscarPaginadaPorPacienteId(UUID pacienteId, UUID cursor, int limit); 
    List<Consulta> buscarPaginada(UUID cursor,
                                  UUID pacienteId,
                                  UUID medicoId,
                                  StatusConsulta status,
                                  Instant dataInicio,
                                  Instant dataFim,
                                  int limit);
    boolean existeConflitoHorarioMedicoIgnorandoConsulta(UUID medicoId, Instant inicio, Instant fim, UUID consulta);
    List<Consulta> buscarConsultasAtivasPorMedicoEData(UUID medicoId, Instant inicioDia, Instant fimDia);
    List<Consulta> buscarConsultasParaMarcarFaltou(Instant limite);
    
}
