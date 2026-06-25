package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.BloqueioAgendaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarBloqueioAgendaPaginado {
    private final BloqueioAgendaRepository repository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<BloqueioAgendaResponse> execute(
            UUID cursor,
            UUID medicoId,
            LocalDate dataInicio,
            LocalDate dataFim,
            int limit
    ) {
        List<BloqueioAgenda> bloqueioAgenda = repository.buscaPaginada(
                cursor,
                medicoId,
                dataInicio,
                dataFim,
                limit + 1
        );
        boolean hasNext = bloqueioAgenda.size() > limit;

        if(hasNext) {
            bloqueioAgenda.removeLast();
        }

        return new PaginacaoResponse<>(
                bloqueioAgenda.stream().map(BloqueioAgendaResponse::of).toList(),
                hasNext ? bloqueioAgenda.getLast().getId() : null,
                hasNext
        );
    }
}
