package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataFilaAtendimento extends JpaRepository<FilaAtendimento, UUID>, JpaSpecificationExecutor<FilaAtendimento> {
    @EntityGraph(attributePaths = {"medico", "consulta", "sala"})
    Optional<FilaAtendimento> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = {"medico","paciente"})
    Page<FilaAtendimento> findAll(Specification spec, Pageable pageable);
}
