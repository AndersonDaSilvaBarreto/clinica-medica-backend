package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.VerificacaoRegistroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificarRegistroUseCase {
    private final UsuarioRepository repository;
    public void execute(VerificacaoRegistroDto dto) {

    }
}
