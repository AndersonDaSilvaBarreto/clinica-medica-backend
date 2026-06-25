package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
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
public class SalaAtendimentoRepositoryImpl implements SalaAtendimentoRepository {
    private final SpringDataSalaAtendimento springDataSalaAtendimento;
    public static final String CACHE_POR_ID = "salaAtendimentoPorId";
    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#salaAtendimento.id")
    })
    public SalaAtendimento salvar(SalaAtendimento salaAtendimento) {
        return springDataSalaAtendimento.save(salaAtendimento);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#salaAtendimento.id")
    })
    public void deletar(SalaAtendimento salaAtendimento) {
        springDataSalaAtendimento.delete(salaAtendimento);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID,key = "#salaAtendimentoId")
    public SalaAtendimento buscarPorId(UUID salaAtendimentoId) {
        return springDataSalaAtendimento.findById(salaAtendimentoId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.SALA_ATENDIMENTO,
                        salaAtendimentoId
                )
        );
    }

    @Override
    public List<SalaAtendimento> buscaPaginada(UUID cursor, String busca, Boolean ativa, int limit) {
        return springDataSalaAtendimento.buscaPaginada(
                cursor,busca != null? "%" + busca.toLowerCase() + "%": null,ativa, PageRequest.of(0,limit)
        );
    }
}
