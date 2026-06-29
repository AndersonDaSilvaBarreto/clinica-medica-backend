package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.FilaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense.FilaAtendimentoSpecifications;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.FilaAtendimentoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponseCursorInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaPaginadaFilaUseCase {
    private final FilaAtendimentoRepository filaAtendimentoRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponseCursorInteger<FilaAtendimentoResponse> execute(
            Integer ordemFila,
            UUID medicoId,
            StatusFila statusFila,
            LocalDate dataDia,
            int limit
    ) {
        Specification<FilaAtendimento> specs = Specification
                .where(FilaAtendimentoSpecifications.porMedicoId(medicoId))
                .and(FilaAtendimentoSpecifications.porStatusIgualA(statusFila))
                .and(FilaAtendimentoSpecifications.porDataDia(dataDia))
                .and(FilaAtendimentoSpecifications.ordemFilaMaiorQue(ordemFila));
        Pageable pageable = PageRequest.of(0, limit + 1, Sort.by(Sort.Direction.ASC ,"ordemFila"));
        List<FilaAtendimento> fila = filaAtendimentoRepository.buscaPaginada(specs,pageable);
        boolean hasNext = fila.size() > limit;
        List<FilaAtendimentoResponse> responses = fila.stream()
                .limit(limit)
                .map(FilaAtendimentoResponse::fromEntity)
                .toList();
        return new PaginacaoResponseCursorInteger<>(
                responses,
                hasNext ? responses.getLast().ordemFila() : null,
                hasNext
        );

    }
}
