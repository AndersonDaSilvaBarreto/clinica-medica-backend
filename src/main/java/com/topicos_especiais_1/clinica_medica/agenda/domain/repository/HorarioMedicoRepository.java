package com.topicos_especiais_1.clinica_medica.agenda.domain.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;

public interface HorarioMedicoRepository {

    HorarioMedico salvar(HorarioMedico horario);

    void salvarTodos(List<HorarioMedico> horarios);

    Optional<HorarioMedico> buscarPorMedicoIdEDataHora(UUID medicoId, Instant dataHora);

    List<HorarioMedico> buscarPorMedicoId(UUID medicoId);

    List<HorarioMedico> buscarPorMedicoIdEPeriodo(UUID medicoId, Instant inicio, Instant fim);

    Optional<Instant> buscarDataMaximaGerada(UUID medicoId);

    boolean existePorMedicoIdEDataHora(UUID medicoId, Instant dataHora);
}
