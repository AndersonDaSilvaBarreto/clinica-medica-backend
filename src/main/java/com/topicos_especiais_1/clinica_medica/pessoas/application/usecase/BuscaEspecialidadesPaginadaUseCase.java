package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.EspecialidadeResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaEspecialidadesPaginadaUseCase {
    private final EspecialidadeRepository especialidadeRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<EspecialidadeResponse> execute(
            UUID cursor,
            String busca,
            int limit
    ) {
        List<Especialidade> especialidades =  especialidadeRepository.buscaPaginada(
                cursor,
                busca,
                limit + 1

        );
        boolean hasNext = especialidades.size() > limit;
        UUID nextCursor = null;
        if(hasNext) {
            especialidades.removeLast();
            nextCursor = especialidades.getLast().getId();
        }
        List<EspecialidadeResponse> response = especialidades.stream()
                .map((EspecialidadeResponse::ofEspecialidade))
                .toList();
        return new PaginacaoResponse<>(
                response,
               nextCursor,
                hasNext
        );

    }
}
