package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Convenio;

import java.util.Optional;
import java.util.UUID;

public interface ConvenioRepository {
    Convenio salvar(Convenio convenio);
    Convenio atualizar(Convenio convenio);
    Convenio buscarPorId(UUID id);
    boolean existePorid(UUID id);
    void deletarPorId(UUID id);

}
