package com.topicos_especiais_1.clinica_medica.dashboard.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.infra.persistence.ConsultaSpecifications;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.ConsultasPeriodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ConsultasPeriodoUseCase {
    private final ConsultaRepository consultaRepository;
    private final ZoneId fuso = ZoneId.of("America/Sao_Paulo");

    public ConsultasPeriodoResponse execute(LocalDate dataInicio, LocalDate dataFim) {
        Instant inicioConvertido = dataInicio != null ?dataInicio.atStartOfDay(fuso).toInstant() : null;
        Instant fimConvertido = dataFim != null ? dataFim.atTime(LocalTime.MAX).atZone(fuso).toInstant() : null;

        Specification<Consulta> filtroPeriodo = Specification
                .where(ConsultaSpecifications.dataHoraInicioDepoisDe(inicioConvertido))
                .and(ConsultaSpecifications.dataHoraInicioAntesDe(fimConvertido));
        CompletableFuture<Long> realizadasFuture = CompletableFuture.supplyAsync(() -> {
            Specification<Consulta> specs = filtroPeriodo.and(ConsultaSpecifications.porStatus(StatusConsulta.FINALIZADA));
            return consultaRepository.contarComSpecs(specs);
        });
        CompletableFuture<Long> canceladasFuture = CompletableFuture.supplyAsync(() -> {
            Specification<Consulta> specs = filtroPeriodo.and(ConsultaSpecifications.porStatus(StatusConsulta.CANCELADA));
            return consultaRepository.contarComSpecs(specs);
        });

        CompletableFuture.allOf(realizadasFuture,canceladasFuture).join();
        return new ConsultasPeriodoResponse(realizadasFuture.join(),canceladasFuture.join());


    }
}
