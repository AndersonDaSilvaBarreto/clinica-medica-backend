package com.topicos_especiais_1.clinica_medica.agenda.application.service;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Gera os slots de atendimento (HorarioMedico) para um médico.
 *
 * <p>Regra de janela: os slots sempre cobrem exatamente 1 mês a partir de HOJE.
 * Ex: hoje é dia 15 → gera até dia 15 do próximo mês (exclusive).
 *     hoje é dia 16 → gera até dia 16 do próximo mês (exclusive).
 *
 * <p>O método {@link #gerarSlotsFaltantes} é idempotente: ignora slots já existentes
 * via constraint unique (medico_id, data_hora), por isso pode ser chamado pelo
 * scheduler diário sem risco de duplicata.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeradorHorarioMedicoService {

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    private final HorarioMedicoRepository horarioMedicoRepository;

    /**
     * Gera os slots mensais faltantes para o médico.
     * Chamado na criação do médico e pelo scheduler diário.
     */
    @Transactional
    public void gerarSlotsFaltantes(Medico medico) {
        if (medico.getHorariosAtendimento() == null || medico.getHorariosAtendimento().isEmpty()) {
            log.debug("Médico {} não possui horários de atendimento cadastrados. Nenhum slot gerado.", medico.getId());
            return;
        }

        LocalDate hoje      = LocalDate.now(FUSO_HORARIO);
        LocalDate fimJanela = hoje.plusMonths(1); // exclusive

        // Descobrir a partir de qual data gerar (evitar reprocessar o passado)
        LocalDate inicioGeracao = horarioMedicoRepository
                .buscarDataMaximaGerada(medico.getId())
                .map(maxInstant -> maxInstant.atZone(FUSO_HORARIO).toLocalDate().plusDays(1))
                .orElse(hoje);

        if (!inicioGeracao.isBefore(fimJanela)) {
            log.debug("Médico {} já tem slots gerados até {}. Nada a fazer.", medico.getId(), fimJanela);
            return;
        }

        List<HorarioMedico> novos = new ArrayList<>();
        Set<HorarioAtendimento> horariosAtendimento = medico.getHorariosAtendimento();

        for (LocalDate dia = inicioGeracao; dia.isBefore(fimJanela); dia = dia.plusDays(1)) {
            final LocalDate diaFinal = dia;
            DiaSemana diaSemana = DiaSemana.de(dia.getDayOfWeek());

            horariosAtendimento.stream()
                    .filter(h -> h.getDiaSemana() == diaSemana)
                    .forEach(h -> {
                        LocalTime cursor = h.getHoraInicio();
                        int duracaoMin   = medico.getTempoConsultaMinutos();

                        while (!cursor.plusMinutes(duracaoMin).isAfter(h.getHoraFim())) {
                            Instant dataHora = LocalDateTime.of(diaFinal, cursor)
                                    .atZone(FUSO_HORARIO)
                                    .toInstant();

                            // Proteção extra: não duplicar slot já salvo
                            if (!horarioMedicoRepository.existePorMedicoIdEDataHora(medico.getId(), dataHora)) {
                                novos.add(HorarioMedico.criar(medico, dataHora));
                            }
                            cursor = cursor.plusMinutes(duracaoMin);
                        }
                    });
        }

        if (!novos.isEmpty()) {
            horarioMedicoRepository.salvarTodos(novos);
            log.info("Gerados {} slots para o médico {} ({} → {}).",
                    novos.size(), medico.getId(), inicioGeracao, fimJanela);
        }
    }
}
