package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.MedicoEspecialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.MedicoEspecialidadeId;

public interface MedicoEspecialidadeRepository {
    MedicoEspecialidade salvar(MedicoEspecialidade medicoEspecialidade);
    MedicoEspecialidade buscarPorId(MedicoEspecialidadeId medicoEspecialidadeId);
    boolean existePorId(MedicoEspecialidadeId medicoEspecialidadeId);
    void deletar(MedicoEspecialidade medicoEspecialidade);
}
