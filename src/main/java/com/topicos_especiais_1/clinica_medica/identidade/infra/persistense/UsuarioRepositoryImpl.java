package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final SpringDataUsuarioRepository repository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "usuarioPorId", allEntries = true),
            @CacheEvict(value = "usuarioPorEmail", allEntries = true)
    })
    public Usuario salvar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "usuarioPorId", allEntries = true),
            @CacheEvict(value = "usuarioPorEmail", allEntries = true)
    })
    public Usuario atualizar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Cacheable(value = "usuarioPorId", key = "#usuarioId")
    public Optional<Usuario> buscarPorId(@NonNull UUID usuarioId) {
        return repository.findById(usuarioId);
    }

    @Override
    @Cacheable(value = "usuarioPorEmail", key = "#email")
    public Optional<Usuario> buscarPorEmail(@NonNull Email email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existePorEmail(@NonNull Email email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existePorid(@NonNull UUID id) {
        return repository.existsById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "usuarioPorId", allEntries = true),
            @CacheEvict(value = "usuarioPorEmail", allEntries = true)
    })
    public void deletarPorId(@NonNull UUID id) {
        repository.deleteById(id);
    }
}
