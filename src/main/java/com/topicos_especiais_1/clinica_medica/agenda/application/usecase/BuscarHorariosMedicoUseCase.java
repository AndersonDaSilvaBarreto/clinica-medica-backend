package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.enums.StatusHorarioMedico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.UUID;

/**
 * Retorna os slots mensais de um médico, com opção de filtrar por status.
 */
@Service
@RequiredArgsConstructor
public class BuscarHorariosMedicoUseCase {

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    private final HorarioMedicoRepository horarioMedicoRepository;

    /**
     * Retorna todos os slots do médico na janela de 1 mês a partir de hoje.
     *
     * @param medicoId ID do médico
     * @param status   Filtro opcional (null = todos)
     */
    @Transactional(readOnly = true)
    public List<HorarioMedico> execute(UUID medicoId, StatusHorarioMedico status) {
        Instant inicio = LocalDate.now(FUSO_HORARIO)
                .atStartOfDay(FUSO_HORARIO)
                .toInstant();

        Instant fim = LocalDate.now(FUSO_HORARIO)
                .plusMonths(1)
                .atStartOfDay(FUSO_HORARIO)
                .toInstant();

        List<HorarioMedico> slots = horarioMedicoRepository.buscarPorMedicoIdEPeriodo(medicoId, inicio, fim);

        if (status != null) {
            slots = slots.stream()
                    .filter(h -> h.getStatus() == status)
                    .toList();
        }

        return slots;
    }
}
