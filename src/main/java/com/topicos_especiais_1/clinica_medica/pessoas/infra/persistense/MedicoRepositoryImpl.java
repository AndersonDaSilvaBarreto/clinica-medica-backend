package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MedicoRepositoryImpl implements MedicoRepository {
    private static final String CACHE_POR_ID = "medicoPorId";
    private static final String CACHE_POR_ID_COM_ESPECIALIDADES = "medicoPorIdComEspecialidades";
    private static final String CACHE_POR_CRM = "medicoPorCrm";
    private final SpringDataMedicoRepository repository;


    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#medico.id"),
            @CacheEvict(value = CACHE_POR_CRM, key = "#medico.crm"),
            @CacheEvict(value = CACHE_POR_ID_COM_ESPECIALIDADES, key = "#medico.id")
    })
    public Medico salvar(Medico medico) {
        return repository.save(medico);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID,key = "#medicoId")
    public Medico buscarPorId(UUID medicoId) {
        return repository.findById(medicoId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.MEDICO,
                        medicoId
                )
        );
    }

    @Override
    @Cacheable(value = CACHE_POR_ID_COM_ESPECIALIDADES, key = "#medicoId")
    public Medico buscarPorIdComEspecialidades(UUID medicoId) {
        return repository.buscarPorIdComEspecialidades(medicoId).orElseThrow(() ->
                EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.MEDICO,
                        medicoId
                ));
    }

    @Override
    @Cacheable(value = CACHE_POR_CRM, key = "#crm")
    public Medico buscarPorCrm(Crm crm) {
        return repository.findByCrm(crm).orElseThrow(
                () ->
                        EntidadeNaoEncontradaException.porCampo(
                                EntidadeNaoEncontradaException.MEDICO,
                                "Crm",
                                crm.toString()
                        )
        );
    }

    @Override
    public boolean existePorCrm(Crm crm) {
        return repository.existsByCrm(crm);
    }

    @Override
    public List<Medico> buscaPaginada(UUID cursor, String busca, int limit) {
        return repository.buscaPaginada(
                cursor,
                busca,
                PageRequest.of(0, limit)
        );
    }
}
