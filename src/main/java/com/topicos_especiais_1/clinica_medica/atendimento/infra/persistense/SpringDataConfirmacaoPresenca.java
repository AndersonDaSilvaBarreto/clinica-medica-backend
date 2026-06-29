package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.ConfirmacaoPresenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SpringDataConfirmacaoPresenca extends JpaRepository<ConfirmacaoPresenca, UUID>, JpaSpecificationExecutor<ConfirmacaoPresenca> {
}
