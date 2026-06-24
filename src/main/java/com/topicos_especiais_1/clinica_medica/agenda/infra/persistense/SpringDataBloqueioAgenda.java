package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBloqueioAgenda extends JpaRepository<BloqueioAgenda, UUID> {
}
