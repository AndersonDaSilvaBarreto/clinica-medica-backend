package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.CriarBloqueioAgendaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.api.MedicoApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarBloqueioAgendaUseCase {
    private final BloqueioAgendaRepository repository;
    private final MedicoApi medicoApi;
    @Transactional
    public void execute(CriarBloqueioAgendaRequest request) {
        Medico medico = medicoApi.buscarPorId(request.medicoId());
        BloqueioAgenda bloqueioAgenda = BloqueioAgenda.create(
                medico,
                request.dataInicio(),
                request.dataFim(),
                request.motivo()
        );
        repository.salvar(bloqueioAgenda);
    }
}
