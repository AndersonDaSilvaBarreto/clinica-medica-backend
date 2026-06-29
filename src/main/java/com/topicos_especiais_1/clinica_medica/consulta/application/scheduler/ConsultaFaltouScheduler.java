package com.topicos_especiais_1.clinica_medica.consulta.application.scheduler;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaFaltouScheduler {

    // Simplificado para remover a necessidade do import java.time.Duration
    private static final long TOLERANCIA_MINUTOS = 30;

    private final ConsultaRepository consultaRepository;

    @Transactional
    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 60 * 1000) 
    public void marcarConsultasFaltou() {
        Instant limite = Instant.now().minus(TOLERANCIA_MINUTOS, ChronoUnit.MINUTES);

        List<Consulta> pendentes = consultaRepository.buscarConsultasParaMarcarFaltou(limite);

        if (pendentes.isEmpty()) {
            return;
        }

        log.info("[Scheduler] Marcando {} consulta(s) como FALTOU (tolerância: {} min).",
                pendentes.size(), TOLERANCIA_MINUTOS);

        int marcadas = 0;
        for (Consulta consulta : pendentes) {
            try {
                consulta.marcarFaltou();
                consultaRepository.salvar(consulta); 
                marcadas++;
            } catch (Exception e) {
                log.warn("[Scheduler] Não foi possível marcar consulta {} como FALTOU: {}",
                        consulta.getId(), e.getMessage());
            }
        }

        log.info("[Scheduler] {} consulta(s) marcada(s) como FALTOU.", marcadas);
    }
}
