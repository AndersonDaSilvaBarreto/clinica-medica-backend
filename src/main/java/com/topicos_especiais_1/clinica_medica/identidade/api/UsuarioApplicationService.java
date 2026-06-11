package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioApi {
    private final UsuarioRepository repository;
    @Override
    public UsuarioResumo buscarPorId(UUID id) {
        var usuario = repository.buscarPorId(id);
       return UsuarioResumo.ofUsuario(usuario);

    }

    @Override
    public UsuarioResumo buscarPorEmail(Email email) {
        var usuario = repository.buscarPorEmail(email);
        return UsuarioResumo.ofUsuario(usuario);
    }
}
