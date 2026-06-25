package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.RecepcionistaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaRecepcionistaPaginadoUseCase {
    private final RecepcionistaRepository repository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<RecepcionistaResponse> execute(UUID cursor, String busca, int limit) {
        var recepcionistas = repository.buscaPaginada(cursor, busca, limit + 1 );
        boolean hasNext = recepcionistas.size() > limit;
        if(hasNext) {
            recepcionistas.removeLast();
        }
        return new PaginacaoResponse<>(
                recepcionistas.stream().map(RecepcionistaResponse::of).toList(),
                hasNext? recepcionistas.getLast().getId() : null,
                hasNext
        );
    }
}
