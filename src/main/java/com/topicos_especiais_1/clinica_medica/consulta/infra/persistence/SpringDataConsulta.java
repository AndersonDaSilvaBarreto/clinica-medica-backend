package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SpringDataConsulta extends JpaRepository<Consulta, UUID>, JpaSpecificationExecutor<Consulta> {
    @Override
    @EntityGraph(attributePaths = {
            "paciente",
            "paciente.usuario",
            "medico",
            "medico.usuario"
    })
    Page<Consulta> findAll(@NonNull Specification<Consulta> spec, @NonNull Pageable pageable);
}
