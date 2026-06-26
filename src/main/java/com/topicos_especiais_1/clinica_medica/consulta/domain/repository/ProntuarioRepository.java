package com.topicos_especiais_1.clinica_medica.consulta.domain.repository;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;

import java.util.UUID;

public interface ProntuarioRepository {
    Prontuario salvar(Prontuario prontuario);
    boolean existePorConsulta(Consulta consulta);

    Prontuario porConsultaId(UUID consultaId);
}
