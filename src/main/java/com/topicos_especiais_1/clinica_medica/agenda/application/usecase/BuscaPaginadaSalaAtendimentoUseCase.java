package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.SalaAtendimentoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaPaginadaSalaAtendimentoUseCase {
    private final SalaAtendimentoRepository salaAtendimentoRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<SalaAtendimentoResponse> execute(
            UUID cursor,
            String busca,
            Boolean ativa,
            int limit
    ) {
        var salas = salaAtendimentoRepository.buscaPaginada(cursor,busca,ativa, limit + 1);
        boolean hasNext = salas.size() > limit;
        if(hasNext) {
            salas.removeLast();
        }
        return new PaginacaoResponse<>(
                salas.stream().map(SalaAtendimentoResponse::from).toList(),
                hasNext ? salas.getLast().getId(): null,
                hasNext
        );
    }
}
