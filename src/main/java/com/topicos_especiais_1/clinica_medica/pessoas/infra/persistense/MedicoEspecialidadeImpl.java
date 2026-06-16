package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.MedicoEspecialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoEspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.MedicoEspecialidadeId;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MedicoEspecialidadeImpl implements MedicoEspecialidadeRepository {
    private static final String CACHE_POR_ID = "MedicoEspecialidadeId";
    private final SpringDataMedicoEspecialidadeRepository repository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#medicoEspecialidade.id")
    })
    public MedicoEspecialidade salvar(MedicoEspecialidade medicoEspecialidade) {
        return repository.save(medicoEspecialidade);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#medicoEspecialidadeId")
    public MedicoEspecialidade buscarPorId(MedicoEspecialidadeId medicoEspecialidadeId) {
        return repository.findById(medicoEspecialidadeId).orElseThrow(() ->
                EntidadeNaoEncontradaException.porCampo(
                        EntidadeNaoEncontradaException.MEDICO_ESPECIALIDADE,
                        "medico id e especialidade id",
                        medicoEspecialidadeId
                ));
    }

    @Override
    public boolean existePorId(MedicoEspecialidadeId medicoEspecialidadeId) {
        return repository.existsById(medicoEspecialidadeId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#medicoEspecialidade.id")
    })
    public void deletar(MedicoEspecialidade medicoEspecialidade) {
        repository.delete(medicoEspecialidade);
    }
}
