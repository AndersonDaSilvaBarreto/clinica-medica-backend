package com.topicos_especiais_1.clinica_medica.pessoas.api;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecepcionistaApiImpl implements RecepcionistaApi {
    private final RecepcionistaRepository repository;
    @Override
    public Recepcionista buscarPorUsuarioId(UUID usuarioId) {
        return repository.buscarPorUsuarioId(usuarioId);
    }
}
