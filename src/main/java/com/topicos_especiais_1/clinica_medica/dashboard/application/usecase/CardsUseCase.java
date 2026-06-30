package com.topicos_especiais_1.clinica_medica.dashboard.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.infra.persistence.ConsultaSpecifications;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.CardsResponse;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense.MedicoSpecifications;
import com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense.PacienteSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CardsUseCase {
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final ZoneId fuso = ZoneId.of("America/Sao_Paulo");
    @Transactional(readOnly = true)
    public CardsResponse execute(LocalDate dataInicio, LocalDate dataFim) {
        Instant inicioConvertido = dataInicio != null ? dataInicio.atStartOfDay(fuso).toInstant(): null;
        Instant fimConvertidoo = dataFim != null ? dataFim.atTime(LocalTime.MAX).atZone(fuso).toInstant() : null;

        CompletableFuture<Long> totalConsultasFuture = CompletableFuture.supplyAsync(() -> {
            Specification<Consulta> specs = Specification
                    .where(ConsultaSpecifications.dataHoraInicioDepoisDe(inicioConvertido))
                    .and(ConsultaSpecifications.dataHoraInicioAntesDe(fimConvertidoo));
            return consultaRepository.contarComSpecs(specs);
        });
        CompletableFuture<Long> medicosAtivosFuture = CompletableFuture.supplyAsync(() -> {
            Specification<Medico> specs = Specification.where(MedicoSpecifications.porAtivoUsuario(true));
            return medicoRepository.contarComSpecs(specs);
        });

        CompletableFuture<Long>  pacientesCadastradosFuture = CompletableFuture.supplyAsync(() -> {
            Specification<Paciente> specs = Specification
                    .where(PacienteSpecifications.porDataCriacaoDepoisDe(inicioConvertido))
                    .and(PacienteSpecifications.porDataCriacaoAntesDe(fimConvertidoo));
            return pacienteRepository.contarComSpecs(specs);
        });

        CompletableFuture<Long> totalPresentesFuture = CompletableFuture.supplyAsync(() -> {

            Specification<Consulta> periodoSpecs = Specification.
                    where(ConsultaSpecifications.dataHoraInicioDepoisDe(inicioConvertido))
                    .and(ConsultaSpecifications.dataHoraInicioAntesDe(fimConvertidoo));

            Specification<Consulta> statusSpecs = Specification
                    .where(ConsultaSpecifications.porStatus(StatusConsulta.PRESENTE))
                    .or(ConsultaSpecifications.porStatus(StatusConsulta.EM_ATENDIMENTO))
                    .or(ConsultaSpecifications.porStatus(StatusConsulta.FINALIZADA));
            return consultaRepository.contarComSpecs(periodoSpecs.and(statusSpecs));
        });
        CompletableFuture.allOf(
                totalConsultasFuture,
                medicosAtivosFuture,
                pacientesCadastradosFuture,
                totalPresentesFuture
        ).join();
        long totalConsultas = totalConsultasFuture.join();
        long totalPresentes = totalPresentesFuture.join();
        long medicosAtivos = medicosAtivosFuture.join();
        long pacienteCadastrados = pacientesCadastradosFuture.join();

        return new CardsResponse(
                totalConsultas,
                medicosAtivos,
                pacienteCadastrados,
                Math.round((double) totalPresentes / totalConsultas * 100)
        );
    }
}
