package com.topicos_especiais_1.clinica_medica.atendimento.domain.repository;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FilaAtendimentoRepository {
    FilaAtendimento salvar(FilaAtendimento filaAtendimento);
    FilaAtendimento buscarPorId(UUID filaAtendimentoId);
    List<FilaAtendimento> buscaPaginada(Specification<FilaAtendimento> specs, Pageable pageable);
    boolean existeConsultaNaFilaDoDia(UUID consultaId, LocalDate data);
}
