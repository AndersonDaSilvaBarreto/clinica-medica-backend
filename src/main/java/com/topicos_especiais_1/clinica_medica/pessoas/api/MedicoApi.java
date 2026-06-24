package com.topicos_especiais_1.clinica_medica.pessoas.api;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;

import java.util.UUID;

public interface MedicoApi {
    Medico buscarPorIdComAgenda(UUID medicoId);
}
