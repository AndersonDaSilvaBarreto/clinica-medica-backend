package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import org.springframework.data.jpa.domain.Specification;

public interface MedicoRepository {
    Medico salvar(Medico medico);
    Medico buscarPorId(UUID medicoId);
    Medico buscarPorIdComEspecialidades(UUID medicoId);
    Medico buscarPorIdComAgenda(UUID medicoId);
    Medico buscarPorCrm(Crm crm);
    Optional<Medico> findByUsuarioId(UUID usuarioId);
    boolean existePorCrm(Crm crm);
    List<Medico> buscaPaginada(UUID cursor, String busca, int limit);
    Medico buscarPorUsuarioId(UUID usuarioId);
    List<Medico> buscarTodosComAgenda();
    long contarComSpecs(Specification<Medico> specs);
}
