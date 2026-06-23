package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.RecepcionistaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarRecepcionistaPorIdUseCase {
    private final RecepcionistaRepository repository;

    public RecepcionistaResponse execute(UUID recepcionistaId) {
        return RecepcionistaResponse.of(repository.buscarPorIdComDatalhes(recepcionistaId));
    }
}
