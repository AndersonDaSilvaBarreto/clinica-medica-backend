package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SpringDataReagendamento extends JpaRepository<ReagendamentoConsulta, UUID>, JpaSpecificationExecutor<ReagendamentoConsulta> {

    @Override
    @EntityGraph(attributePaths = "reagendadoPor")
    Page<ReagendamentoConsulta> findAll(@NonNull Specification<ReagendamentoConsulta> spec, @NonNull Pageable pageable);
}
