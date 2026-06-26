package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ReagendamentoConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaPaginadaReagendamentoConsultaUseCase {
    private final ReagendamentoConsultaRepository reagendamentoConsultaRepository;
    private final ConsultaRepository consultaRepository;

    public PaginacaoResponse<ReagendamentoResponse> execute(
            UUID cursor,
            UUID consultaId,
            UUID pacienteId,
            Instant depoisDe,
            Instant antesDe,
            int limit
    ) {

        Consulta consulta = consultaRepository.buscarPorId(consultaId);
        List<ReagendamentoConsulta> reagendamentoConsultas = reagendamentoConsultaRepository.buscaPaginada(
                cursor,consulta.getId(),pacienteId,depoisDe,antesDe,limit + 1
        );
        boolean hasNext = reagendamentoConsultas.size() > limit;

        List<ReagendamentoResponse> response = reagendamentoConsultas.stream()
                .limit(limit)
                .map(ReagendamentoResponse::fromEntity)
                .toList();
        return new PaginacaoResponse<>(
                response,
                hasNext ? response.getLast().id() : null,
                hasNext
        );
    }
}
