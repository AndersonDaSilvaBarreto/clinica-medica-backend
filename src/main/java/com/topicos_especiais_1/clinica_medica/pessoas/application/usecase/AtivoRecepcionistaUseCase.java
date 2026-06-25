package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtivoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtivoRecepcionistaUseCase {
    private final RecepcionistaRepository repository;

    @Transactional
    public void execute(UUID recepcionistaId, AtivoRequest ativoRequest) {
        var recepcionista = repository.buscarPorIdComDatalhes(recepcionistaId);
        recepcionista.getUsuario().mudarAtivo(ativoRequest.ativo());
        repository.salvar(recepcionista);
    }
}
