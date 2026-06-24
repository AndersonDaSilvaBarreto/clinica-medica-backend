package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BloqueioAgendaImpl implements BloqueioAgendaRepository {
    private final SpringDataBloqueioAgenda springDataBloqueioAgenda;
    private static final String CACHE_POR_ID = "bloqueioAgendaPorId";
    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#bloqueioAgenda.id")
    })
    public BloqueioAgenda salvar(BloqueioAgenda bloqueioAgenda) {
        return springDataBloqueioAgenda.save(bloqueioAgenda);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#bloqueioAgendaId")
    public BloqueioAgenda buscarPorId(UUID bloqueioAgendaId) {
        return null;
    }

    @Override
    public List<BloqueioAgenda> buscaPaginada(UUID cursor, UUID medicoId, LocalDate dataInicio, LocalDate dataFim, int limit) {
        return List.of();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID, key = "#bloqueioAgenda.id")
    })
    public void deletar(BloqueioAgenda bloqueioAgenda) {
        springDataBloqueioAgenda.delete(bloqueioAgenda);
    }
}
