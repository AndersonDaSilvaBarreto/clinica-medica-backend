package com.topicos_especiais_1.clinica_medica.pessoas.domain.service;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Convenio;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;

import java.util.Optional;
import java.util.UUID;

public interface ConvenioRepository {
    Convenio salvar(Convenio convenio);
    Convenio atualizar(Convenio convenio);
    Optional<Convenio> buscarPorId(UUID id);
    Optional<Convenio> buscarPorNome(Nome nome);
    boolean existePorid(UUID id);
    void deletarPorId(UUID id);

}
