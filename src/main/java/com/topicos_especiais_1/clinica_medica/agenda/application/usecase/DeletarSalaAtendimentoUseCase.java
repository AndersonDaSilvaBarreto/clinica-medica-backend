package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletarSalaAtendimentoUseCase {
    private final SalaAtendimentoRepository salaAtendimentoRepository;

    @Transactional
    public void execute(UUID salaId) {
        var sala = salaAtendimentoRepository.buscarPorId(salaId);
        salaAtendimentoRepository.deletar(sala);
    }
}
