package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletarBloqueioAgendaUseCase {
    private final  BloqueioAgendaRepository repository;

    @Transactional
    public void execute(UUID id) {
        BloqueioAgenda bloqueioAgenda = repository.buscarPorId(id);
        repository.deletar(bloqueioAgenda);
    }
}
