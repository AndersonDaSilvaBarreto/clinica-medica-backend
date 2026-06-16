package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
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

    @Override
    public UsuarioResumo trocarTelefone(UUID usuarioId,String telefone) {
        var usuario = repository.buscarPorId(Objects.requireNonNull(usuarioId));
        usuario.mudarTelefone(Telefone.of(telefone));
        var usuarioAtualizado =  repository.atualizar(usuario);
        return UsuarioResumo.ofUsuario(usuarioAtualizado);
    }

    @Override
    public UsuarioResumo trocarDataNascimento(UUID usuarioId,LocalDate dataNascimento) {
        var usuario = repository.buscarPorId(Objects.requireNonNull(usuarioId));
        usuario.mudarDataNascimento(DataNascimento.of(dataNascimento));
        var usuarioAtualizado = repository.atualizar(usuario);
        return UsuarioResumo.ofUsuario(usuarioAtualizado);

    }


}
