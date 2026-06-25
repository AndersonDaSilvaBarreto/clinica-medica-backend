package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AgendaResponse;
import com.topicos_especiais_1.clinica_medica.pessoas.api.MedicoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarAgendasPorMedicoUseCase {
    public final MedicoApi medicoApi;

    public AgendaResponse execute(UUID medicoId) {
        var medico = medicoApi.buscarPorIdComAgenda(medicoId);
        return AgendaResponse.from(medico.getHorariosAtendimento().stream().toList());
    }
}
