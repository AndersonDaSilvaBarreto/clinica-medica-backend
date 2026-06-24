package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaMedicoPaginadoUseUse {
    private final MedicoRepository repository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<MedicoResponse> execute(UUID cursor, String busca, int limit) {
        List<Medico> medicos = repository.buscaPaginada(cursor,busca,limit + 1);
        boolean hasNext = medicos.size() > limit;
        if(hasNext) {
            medicos.removeLast();
        }
        UUID nextCursor = hasNext ? medicos.getLast().getId() : null;

        return new PaginacaoResponse<>(
                medicos.stream()
                        .map(MedicoResponse::of)
                        .toList(),
                nextCursor,
                hasNext
        );

    }
}
