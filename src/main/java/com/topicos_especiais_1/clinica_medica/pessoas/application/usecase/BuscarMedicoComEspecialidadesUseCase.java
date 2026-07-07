package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarMedicoComEspecialidadesUseCase {
    private final MedicoRepository repository;
    private static final String CACHE_POR_ID_COM_ESPECIALIDADES = "medicoPorIdComEspecialidades";

    
    @Caching(evict = {
        @CacheEvict(value = CACHE_POR_ID_COM_ESPECIALIDADES, key = "#medico.id"),
    })

    @Cacheable(value = CACHE_POR_ID_COM_ESPECIALIDADES, key = "#medicoId")
    @Transactional
    public MedicoResponse execute(UUID medicoId) {
        return MedicoResponse.of(repository.buscarPorIdComEspecialidades(medicoId));
    }
}
