package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioNaoEncontradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static final String CACHE_POR_ID = "usuarioPorId";
    private static final String CACHE_POR_EMAIL = "usuarioPorEmail";
    public static final String CACHE_POR_CPF = "usuarioPorCpf";
    private final SpringDataUsuarioRepository repository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = UsuarioRepositoryImpl.CACHE_POR_ID, key = "#usuario.id"),
            @CacheEvict(value = UsuarioRepositoryImpl.CACHE_POR_EMAIL, key = "#usuario.email"),
            @CacheEvict(value = CACHE_POR_CPF,key = "#usuario.cpf")
    })
    public Usuario salvar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#usuario.id" ),
            @CacheEvict(value = CACHE_POR_EMAIL, key = "#usuario.email"),
            @CacheEvict(value = CACHE_POR_CPF,key = "#usuario.cpf")
    })
    public Usuario atualizar(@NonNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#usuarioId")
    public Usuario buscarPorId(@NonNull UUID usuarioId) {
        return repository.findById(usuarioId).
                orElseThrow(() ->
                        EntidadeNaoEncontradaException.porId(
                                EntidadeNaoEncontradaException.USUARIO,
                                usuarioId
                        ));
    }

    @Override
    @Cacheable(value = CACHE_POR_EMAIL, key = "#email")
    public Usuario buscarPorEmail(@NonNull Email email) {

        return repository.findByEmail(email)
                .orElseThrow(() -> EntidadeNaoEncontradaException.porEmail(
                        EntidadeNaoEncontradaException.USUARIO,
                        email
                        )
                );
    }

    @Override
    @Cacheable(value = UsuarioRepositoryImpl.CACHE_POR_CPF, key = "#cpf")
    public Usuario buscarPorCpf(Cpf cpf) {
        return repository.findByCpf(cpf).orElseThrow(() -> UsuarioNaoEncontradoException.porCpf(cpf));
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
    public boolean existePorCpf(Cpf cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#usuario.id"),
            @CacheEvict(value = CACHE_POR_EMAIL, key = "#usuario.email"),
            @CacheEvict(value = CACHE_POR_CPF,key = "#usuario.cpf")
    })
    public void deletar(@NonNull Usuario usuario) {
        repository.delete(usuario);
    }
}
