package com.topicos_especiais_1.clinica_medica.atendimento.domain.repository;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.ConfirmacaoPresenca;

import java.util.UUID;

public interface ConfirmacaoPresencaRepository {
    ConfirmacaoPresenca salvar(ConfirmacaoPresenca confirmacaoPresenca);
    boolean existePorConsultaId(UUID consultaId);

}
