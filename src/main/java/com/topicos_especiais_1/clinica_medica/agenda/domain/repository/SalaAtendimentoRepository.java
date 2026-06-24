package com.topicos_especiais_1.clinica_medica.agenda.domain.repository;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;

import java.util.List;
import java.util.UUID;

public interface SalaAtendimentoRepository {
    SalaAtendimento salvar(SalaAtendimento salaAtendimento);
    void deletar(SalaAtendimento salaAtendimento);
    SalaAtendimento buscarPorId(UUID salaAtendimentoId);
    List<SalaAtendimento> buscaPaginada(UUID cursor, String busca, Boolean ativa, int limit);
}
