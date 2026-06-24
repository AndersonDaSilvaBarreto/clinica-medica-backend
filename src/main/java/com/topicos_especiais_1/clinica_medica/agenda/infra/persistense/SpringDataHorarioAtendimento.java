package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataHorarioAtendimento extends JpaRepository<HorarioAtendimento, UUID> {
}
