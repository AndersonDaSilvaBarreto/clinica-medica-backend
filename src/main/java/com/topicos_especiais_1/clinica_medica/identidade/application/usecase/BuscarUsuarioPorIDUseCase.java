package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioPorIDUseCase {
    private final UsuarioRepository repository;
    @Transactional(readOnly = true)
    public UsuarioResponse execute(UUID id) {
        Usuario usuario = repository.buscarPorId(id);
        return UsuarioResponse.fromEntity(usuario);
    }
}
