package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;

import java.util.UUID;

public interface MedicoRepository {
    Medico salvar(Medico medico);
    Medico buscarPorId(UUID medicoId);
    Medico buscarPorCrm(Crm crm);

}
