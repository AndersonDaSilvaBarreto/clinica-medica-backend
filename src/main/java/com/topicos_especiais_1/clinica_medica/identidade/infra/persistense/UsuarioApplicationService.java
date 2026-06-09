package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioNaoEncontradoException;
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
        var usuario = repository.buscarPorId(id).orElseThrow(() -> UsuarioNaoEncontradoException.porId(id));
       return UsuarioResumo.ofUsuario(usuario);

    }

    @Override
    public UsuarioResumo buscarPorEmail(Email email) {
        var usuario = repository.buscarPorEmail(email).orElseThrow(() -> UsuarioNaoEncontradoException.porEmail(email));
        return UsuarioResumo.ofUsuario(usuario);
    }
}
