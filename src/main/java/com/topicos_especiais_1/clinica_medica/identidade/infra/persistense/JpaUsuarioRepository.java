package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUsuarioRepository implements UsuarioRepository {
    private final SpringDataUsuarioRepository repository;

    @Override
    public Usuario salvar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario atualizar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(@NonNull UsuarioId usuarioId) {
        return repository.findById(usuarioId);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(@NonNull Email email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existePorEmail(@NonNull Email email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existePorid(@NonNull UsuarioId usuarioId) {
        return repository.existsById(usuarioId);
    }

    @Override
    public void deletarPorId(@NonNull UsuarioId id) {
        repository.deleteById(id);
    }
}
