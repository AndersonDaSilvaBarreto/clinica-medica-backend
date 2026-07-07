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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeradorHorarioMedicoService {

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    private final HorarioMedicoRepository horarioMedicoRepository;

    @Transactional
    public void gerarSlotsFaltantes(Medico medico) {
        log.info("Entrou em gerarSlotsFaltantes");
        if (medico.getHorariosAtendimento() == null || medico.getHorariosAtendimento().isEmpty()) {
            log.debug("Médico {} não possui horários de atendimento cadastrados. Nenhum slot gerado.", medico.getId());
            return;
        }

        LocalDate hoje = LocalDate.now(FUSO_HORARIO);
        LocalDate fimJanela = hoje.plusMonths(1); // exclusive

        LocalDate inicioGeracao = horarioMedicoRepository
                .buscarDataMaximaGerada(medico.getId())
                .map(maxInstant -> maxInstant.atZone(FUSO_HORARIO).toLocalDate().plusDays(1))
                .orElse(hoje);

        if (!inicioGeracao.isBefore(fimJanela)) {
            log.debug("Médico {} já tem slots gerados até {}. Nada a fazer.", medico.getId(), fimJanela);
            return;
        }

        Instant inicioPeriodo = inicioGeracao.atStartOfDay(FUSO_HORARIO).toInstant();
        Instant fimPeriodo = fimJanela.atStartOfDay(FUSO_HORARIO).toInstant();

        Set<Instant> datasExistentes = horarioMedicoRepository
                .buscarPorMedicoIdEPeriodo(medico.getId(), inicioPeriodo, fimPeriodo)
                .stream()
                .map(HorarioMedico::getDataHora)
                .collect(Collectors.toSet());

        log.info("Horários existentes: {}", datasExistentes.size());
        List<HorarioMedico> novos = new ArrayList<>();
        Set<HorarioAtendimento> horariosAtendimento = medico.getHorariosAtendimento();

        log.info("Iniciando geração dos slots...");
        for (LocalDate dia = inicioGeracao; dia.isBefore(fimJanela); dia = dia.plusDays(1)) {
            log.info("Processando dia {}", dia);
            final LocalDate diaFinal = dia;
            DiaSemana diaSemana = DiaSemana.de(dia.getDayOfWeek());

            horariosAtendimento.stream()
                    .filter(h -> h.getDiaSemana() == diaSemana)
                    .forEach(h -> {
                        LocalTime cursor = h.getHoraInicio();
                        int duracaoMin = medico.getTempoConsultaMinutos();

                        while (!cursor.isAfter(h.getHoraFim().minusMinutes(duracaoMin))) {
                            log.info("Cursor={}", cursor);
                            Instant dataHora = LocalDateTime.of(diaFinal, cursor)
                                    .atZone(FUSO_HORARIO)
                                    .toInstant();

                            if (!datasExistentes.contains(dataHora)) {
                                novos.add(HorarioMedico.criar(medico, dataHora));
                            }
                            cursor = cursor.plusMinutes(duracaoMin);
                        }
                    });
        }
        log.info("Fim da geração.");
        if (!novos.isEmpty()) {
            log.info("Quantidade de novos slots: {}", novos.size());
            horarioMedicoRepository.salvarTodos(novos);
            log.info("Gerados {} slots para o médico {} ({} → {}).",
                    novos.size(), medico.getId(), inicioGeracao, fimJanela);
        }
    }
}
