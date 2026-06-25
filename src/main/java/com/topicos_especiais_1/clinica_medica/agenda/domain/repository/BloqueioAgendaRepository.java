package com.topicos_especiais_1.clinica_medica.agenda.domain.repository;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BloqueioAgendaRepository {
    BloqueioAgenda salvar(BloqueioAgenda bloqueioAgenda);
    BloqueioAgenda buscarPorId(UUID bloqueioAgendaId);
    List<BloqueioAgenda> buscaPaginada(UUID cursor, UUID medicoId, LocalDate dataInicio, LocalDate dataFim, int limit);
    void deletar(BloqueioAgenda bloqueioAgenda);
    boolean existeBloquioAtivoParaData(UUID medicoId, LocalDate data);
}
