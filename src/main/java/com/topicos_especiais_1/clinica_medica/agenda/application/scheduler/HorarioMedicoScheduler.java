package com.topicos_especiais_1.clinica_medica.agenda.application.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.topicos_especiais_1.clinica_medica.agenda.application.service.GeradorHorarioMedicoService;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class HorarioMedicoScheduler {

    private final MedicoRepository medicoRepository;
    private final GeradorHorarioMedicoService geradorService;

    @Scheduled(cron = "0 1 0 * * *", zone = "America/Sao_Paulo")
    public void expandirJanelaMensal() {
        log.info("[Scheduler] Iniciando expansão diária da janela de horários dos médicos...");

        List<Medico> medicos = medicoRepository.buscarTodosComAgenda();

        int total = 0;
        for (Medico medico : medicos) {
            try {
                geradorService.gerarSlotsFaltantes(medico);
                total++;
            } catch (Exception e) {
                log.error("[Scheduler] Erro ao gerar slots para o médico {}: {}", medico.getId(), e.getMessage(), e);
            }
        }

        log.info("[Scheduler] Expansão concluída. {} médico(s) processado(s).", total);
    }
}
