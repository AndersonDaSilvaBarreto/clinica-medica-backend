package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EspecialidadeRepository {
    Especialidade salvar(Especialidade especialidade);
    Especialidade atualizar(Especialidade especialidade);
    Especialidade buscarPorId(UUID especialidadeId);
    boolean existePorNome(Nome nome);
    void deletar(Especialidade especialidade);
    List<Especialidade> buscaPaginada(UUID cursor, String busca, int limit);
    Especialidade buscarPorIdMedicoId(UUID especialidadeId, UUID medicoId);
}
