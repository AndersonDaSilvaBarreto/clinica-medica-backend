package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import java.util.UUID; // Ajuste o pacote se necessário

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarMedicoPorUsuarioIdUseCase {

    private final MedicoRepository medicoRepository;

    @Transactional(readOnly = true)
    public MedicoResponse execute(UUID usuarioId) {
        return medicoRepository.findByUsuarioId(usuarioId)
                .map(MedicoResponse::of) 
                .orElseThrow(() -> new RuntimeException("Perfil de médico não encontrado para este usuário"));
    }
}
