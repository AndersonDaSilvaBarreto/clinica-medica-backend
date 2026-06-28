package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataHorarioMedico extends JpaRepository<HorarioMedico, UUID> {

    List<HorarioMedico> findByMedicoIdOrderByDataHoraAsc(UUID medicoId);

    List<HorarioMedico> findByMedicoIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID medicoId, Instant inicio, Instant fim);

    Optional<HorarioMedico> findByMedicoIdAndDataHora(UUID medicoId, Instant dataHora);

    @Query("SELECT MAX(h.dataHora) FROM HorarioMedico h WHERE h.medico.id = :medicoId")
    Optional<Instant> findMaxDataHoraByMedicoId(@Param("medicoId") UUID medicoId);

    boolean existsByMedicoIdAndDataHora(UUID medicoId, Instant dataHora);
}
