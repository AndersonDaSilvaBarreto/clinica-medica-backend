package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtivoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtivoMedicoUseCase {
    private final MedicoRepository medicoRepository;

    @Transactional
    public void execute (UUID medicoId, AtivoRequest request) {
        var medico = medicoRepository.buscarPorIdComEspecialidades(medicoId);
        if (request.ativo()) {
            medico.ativar();
        } else {
            medico.desativar();
        }
        medicoRepository.salvar(medico);
    }
}
