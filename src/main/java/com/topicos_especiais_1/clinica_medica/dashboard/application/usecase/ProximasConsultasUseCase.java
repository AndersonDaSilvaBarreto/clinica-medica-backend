package com.topicos_especiais_1.clinica_medica.dashboard.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.infra.persistence.ConsultaSpecifications;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.ProximasConsultasResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProximasConsultasUseCase {

    private final ConsultaRepository consultaRepository;
    private final ZoneId fuso = ZoneId.of("America/Sao_Paulo");

    public PaginacaoResponse<ProximasConsultasResponse> execute(UUID cursor, LocalDate dataInicio, int limit) {
        LocalDate dataAlvo = (dataInicio != null) ? dataInicio : LocalDate.now(fuso);

        Instant inicioDoDia = dataAlvo.atStartOfDay(fuso).toInstant();
        Instant fimDoDia = dataAlvo.atTime(LocalTime.MAX).atZone(fuso).toInstant();

        Specification<Consulta> specs = Specification.where(ConsultaSpecifications.dataHoraInicioDepoisDe(inicioDoDia))
                .and(ConsultaSpecifications.dataHoraInicioAntesDe(fimDoDia))
                .and(ConsultaSpecifications.idMaiorQue(cursor));
        Pageable pageable = PageRequest.of(0,limit + 1, Sort.by(Sort.Direction.ASC,"id"));
        List<Consulta> consultas = consultaRepository.buscaComSpecsAndPageable(specs,pageable);
        boolean hasNext = consultas.size() > limit;

        List<ProximasConsultasResponse> response = consultas.stream()
                .limit(limit)
                .map(ProximasConsultasResponse::fromEntity)
                .toList();
        return new PaginacaoResponse<>(
                response,
                hasNext ? response.getLast().consultaId() : null,
                hasNext
        );

    }

}
