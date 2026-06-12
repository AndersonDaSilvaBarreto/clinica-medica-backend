package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String CACHE_POR_ID = "pacientePorId";
    private final SpringDataPacienteRepository repository;
    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
    })
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
    })
    public Paciente atualizar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    @Cacheable(value = PacienteRepositoryImpl.CACHE_POR_ID, key = "#id")
    public Paciente buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> EntidadeNaoEncontradaException.porId(
                EntidadeNaoEncontradaException.PACIENTE,
                id
        ));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
    })
    public void deletar(Paciente paciente) {
        repository.delete(paciente);
    }
}
