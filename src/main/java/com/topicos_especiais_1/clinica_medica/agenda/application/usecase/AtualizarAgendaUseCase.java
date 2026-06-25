package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AtualizarAgendaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.api.MedicoApi;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarAgendaUseCase {
    private final MedicoApi medicoApi;
    private final EntityManager entityManager;


    @Transactional
    public void execute(UUID medicoId, AtualizarAgendaRequest request) {
        var medico = medicoApi.buscarPorIdComAgenda(medicoId);
        medico.limparHorariosAtendimento();
        entityManager.flush();
        for(AtualizarAgendaRequest.DiaAgendaRequest dia : request.dias()) {
            for (AtualizarAgendaRequest.PeriodoRequest periodo: dia.periodos()) {
                HorarioAtendimento horario = HorarioAtendimento.create(
                        medico,
                        dia.diaSemana(),
                        periodo.horaInicio(),
                        periodo.horaFim()
                );
                medico.adicionarHorarioAtendimento(horario);
            }
        }

    }
}
