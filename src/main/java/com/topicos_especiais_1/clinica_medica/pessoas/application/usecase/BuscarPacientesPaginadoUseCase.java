package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPacientesPaginadoUseCase {
    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<PacienteResponse> execute(
            UUID cursor,
            int limit,
            String busca) {
        var pacientes = pacienteRepository.buscarPacientes(cursor, limit + 1, busca);
        boolean hasNext = pacientes.size() > limit;
        UUID nextCursor = null;

        if(hasNext) {
            pacientes.removeLast();
            nextCursor = pacientes.getLast().id();
        }
        return new PaginacaoResponse<>(
                pacientes,
                nextCursor,
                hasNext
        );

    }
}
