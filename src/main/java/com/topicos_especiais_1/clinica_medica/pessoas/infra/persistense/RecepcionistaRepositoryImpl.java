package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
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
public class RecepcionistaRepositoryImpl implements RecepcionistaRepository {
    private static final String CACHE_POR_ID = "recepcionistaPorId";
    private static final String CACHE_POR_ID_COM_DETALHES = "RecepcionistaPorIdComDetalhes";
    private static final String CACHE_POR_CPF = "medicoPorCpf";
    private final SpringDataRecepcionistaRepository repository;
    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#recepcionista.id"),
            @CacheEvict(value = CACHE_POR_CPF,key = "#recepcionista.usuario.cpf"),
            @CacheEvict(value = CACHE_POR_ID_COM_DETALHES, key = "#recepcionista.id")
    })
    public Recepcionista salvar(Recepcionista recepcionista) {
        return repository.save(recepcionista);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID,key = "#recepcionistaId")
    public Recepcionista buscarPorId(UUID recepcionistaId) {
        return repository.findById(recepcionistaId).orElseThrow(
                () ->
                        EntidadeNaoEncontradaException.porId(
                                EntidadeNaoEncontradaException.RECEPCIONISTA,
                                recepcionistaId
                        )
        );
    }

    @Override
    @Cacheable(value = CACHE_POR_CPF, key = "#cpf")
    public Recepcionista buscarPorCpf(Cpf cpf) {
        return repository.findByUsuarioCpf(cpf).orElseThrow(
                () ->
                        EntidadeNaoEncontradaException.porCampo(
                                EntidadeNaoEncontradaException.RECEPCIONISTA,
                                "Cpf",
                                cpf.toString()
                        )
        );
    }

    @Override
    @Cacheable(value = CACHE_POR_ID_COM_DETALHES, key = "#recepcionistaId")
    public Recepcionista buscarPorIdComDatalhes(UUID recepcionistaId) {
        return repository.buscarPorIdComDetalhes(recepcionistaId).orElseThrow(
                () ->
                        EntidadeNaoEncontradaException.porId(
                                EntidadeNaoEncontradaException.RECEPCIONISTA,
                                recepcionistaId
                        )
        );
    }

    @Override
    public Recepcionista buscarPorCpfComDetalhes(Cpf cpf) {
        return repository.findByUsuarioCpf(cpf).orElseThrow(
                () ->
                        EntidadeNaoEncontradaException.porCampo(
                                EntidadeNaoEncontradaException.RECEPCIONISTA,
                                "Cpf",
                                cpf.toString()
                        )
        );
    }

    @Override
    public List<Recepcionista> buscaPaginada(UUID cursor, String busca, int limit) {
        return repository.buscaPaginada(cursor, busca, PageRequest.of(0, limit));
    }
}
