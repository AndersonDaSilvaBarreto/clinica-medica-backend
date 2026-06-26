package com.topicos_especiais_1.clinica_medica.consulta.domain.repository;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReagendamentoConsultaRepository {
    ReagendamentoConsulta salvar(ReagendamentoConsulta reagendamentoConsulta);
    List<ReagendamentoConsulta> buscaPaginada(UUID cursor,UUID consultaId, UUID pacienteId, Instant depoisDe, Instant antesDe, int limit);
}
