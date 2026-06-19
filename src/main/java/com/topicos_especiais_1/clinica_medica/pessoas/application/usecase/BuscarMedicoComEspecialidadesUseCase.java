package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarMedicoComEspecialidadesUseCase {
    private final MedicoRepository repository;

    @Transactional
    public MedicoResponse execute(UUID medicoId) {
        return MedicoResponse.of(repository.buscarPorIdComEspecialidades(medicoId));
    }
}
