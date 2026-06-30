package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EspecialidadeRepositoryImpl implements EspecialidadeRepository {
    private static final String CACHE_POR_ID = "especialidadePorId";
    private final SpringDataEspecialidadeRepository repository;
    private final JdbcClient jdbcClient;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CACHE_POR_ID, key = "#especialidade.id" )
            }
    )
    public Especialidade salvar(Especialidade especialidade) {
        return repository.save(especialidade);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CACHE_POR_ID, key = "#especialidade.id" )
            }
    )
    public Especialidade atualizar(Especialidade especialidade) {
        return repository.save(especialidade);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#especialidadeId")
    public Especialidade buscarPorId(UUID especialidadeId) {
        return repository.findById(especialidadeId).orElseThrow(() ->
                EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.ESPECIALIDADE,
                        especialidadeId
                ));
    }

    @Override
    public boolean existePorNome(Nome nome) {
        return repository.existsByNome(nome);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CACHE_POR_ID, key = "#especialidade.id" )
            }
    )
    public void deletar(Especialidade especialidade) {
        repository.delete(especialidade);
    }

    @Override
    public List<Especialidade> buscaPaginada(UUID cursor, String busca, int limit) {
        return repository.buscaPaginada(cursor,
                busca != null? "%" + busca.toLowerCase() + "%" : null,
                PageRequest.of(0,limit));
    }

    @Override
    public Especialidade buscarPorIdMedicoId(UUID especialidadeId, UUID medicoId) {
        return repository.findEspecialidadeByMedicoIdEspecialidadeId(especialidadeId,medicoId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porCampo("Especialidade", "Medico Id e EspecialidadeId", medicoId.toString() + ":" + especialidadeId.toString())
        );
    }
}
