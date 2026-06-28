package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.agenda.application.service.GeradorHorarioMedicoService;
import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AtualizarAgendaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.api.MedicoApi;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarAgendaUseCase {

    private static final ZoneId FUSO = ZoneId.of("America/Sao_Paulo");
    private final GeradorHorarioMedicoService geradorHorarioMedicoService;
    private final HorarioMedicoRepository horarioMedicoRepository;
    private final MedicoApi medicoApi;
    private final EntityManager entityManager;

    @Transactional
    public void execute(UUID medicoId, AtualizarAgendaRequest request) {
        var medico = medicoApi.buscarPorIdComAgenda(medicoId);
        medico.limparHorariosAtendimento();
        entityManager.flush();
        for (AtualizarAgendaRequest.DiaAgendaRequest dia : request.dias()) {
            for (AtualizarAgendaRequest.PeriodoRequest periodo : dia.periodos()) {
                HorarioAtendimento horario = HorarioAtendimento.create(
                        medico,
                        dia.diaSemana(),
                        periodo.horaInicio(),
                        periodo.horaFim()
                );
                medico.adicionarHorarioAtendimento(horario);
            }
        }

        Instant agora = Instant.now();
        Instant fimJanela = LocalDate.now(FUSO).plusMonths(1).atStartOfDay(FUSO).toInstant();

        List<HorarioMedico> slotsDisponiveis = horarioMedicoRepository
                .buscarPorMedicoIdEPeriodo(medicoId, agora, fimJanela)
                .stream()
                .filter(HorarioMedico::isDisponivel)
                .toList();

        // Remove apenas os disponíveis — os ocupados permanecem
        for (HorarioMedico slot : slotsDisponiveis) {
            entityManager.remove(entityManager.contains(slot) ? slot : entityManager.merge(slot));
        }
        entityManager.flush();

        // Regenerar a partir de agora com a nova agenda semanal
        geradorHorarioMedicoService.gerarSlotsFaltantes(medico);

    }
}
