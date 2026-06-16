package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletarEspecialidadePorIdUseCase {
    private final EspecialidadeRepository repository;

    @Transactional
    public void execute(UUID especialidadeId) {
        var especialidade = repository.buscarPorId(especialidadeId);
        repository.deletar(especialidade);
    }
}
