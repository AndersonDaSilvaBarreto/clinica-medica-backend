package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MedicoRepositoryImpl implements MedicoRepository {

    private static final String CACHE_POR_ID = "medicoPorId";
    private static final String CACHE_POR_CRM = "medicoPorCrm";
    private final SpringDataMedicoRepository repository;
    private final UsuarioApi usuarioApi;

    @Override
    @Caching(evict = {
        @CacheEvict(value = CACHE_POR_ID, key = "#medico.id"),
        @CacheEvict(value = CACHE_POR_CRM, key = "#medico.crm"),
    })
    public Medico salvar(Medico medico) {
        var medicoSaved = repository.save(medico);
        usuarioApi.apagarCache(medicoSaved.getUsuario());
        return medicoSaved;
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#medicoId")
    public Medico buscarPorId(UUID medicoId) {
        return repository.findById(medicoId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.MEDICO,
                        medicoId
                )
        );
    }

    @Override
    public Medico buscarPorIdComEspecialidades(UUID medicoId) {
        return repository.buscarPorIdComEspecialidades(medicoId).orElseThrow(()
                -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.MEDICO,
                        medicoId
                ));
    }

    @Override
    public Medico buscarPorIdComAgenda(UUID medicoId) {
        return repository.buscarMedicoComHorariosAtendimento(medicoId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.MEDICO,
                        medicoId
                ));
    }

    @Override
    public Optional<Medico> findByUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    @Cacheable(value = CACHE_POR_CRM, key = "#crm")
    public Medico buscarPorCrm(Crm crm) {
        return repository.findByCrm(crm).orElseThrow(
                ()
                -> EntidadeNaoEncontradaException.porCampo(
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

    @Override
    public Medico buscarPorUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId).orElseThrow(
                () -> EntidadeNaoEncontradaException.porCampo(
                        EntidadeNaoEncontradaException.MEDICO,
                        "usuario",
                        usuarioId
                ));
    }

    @Override
    public List<Medico> buscarTodosComAgenda() {
        return repository.buscarTodosComAgenda();
    }

    @Override
    public long contarComSpecs(Specification<Medico> specs) {
        return repository.count(specs);
    }

    @Override
    public List<Medico> buscarComSpecs(Specification<Medico> specs, Pageable pageable) {
        return repository.findAllComRelacionamentos(specs,pageable).getContent();
    }

}
