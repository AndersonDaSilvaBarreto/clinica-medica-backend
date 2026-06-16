package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;

import java.util.UUID;

public interface EspecialidadeRepository {
    Especialidade salvar(Especialidade especialidade);
    Especialidade atualizar(Especialidade especialidade);
    Especialidade buscarPorId(UUID especialidadeId);
    boolean existePorNome(Nome nome);
    void deletar(Especialidade especialidade);

}
