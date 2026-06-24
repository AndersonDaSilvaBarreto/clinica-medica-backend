package com.topicos_especiais_1.clinica_medica.pessoas.api;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicoApiImpl implements MedicoApi{
    private final MedicoRepository medicoRepository;
    @Override
    public Medico buscarPorIdComAgenda(UUID medicoId) {
        return medicoRepository.buscarPorIdComAgenda(medicoId);
    }
}
