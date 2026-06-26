package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataProntuario extends JpaRepository<Prontuario, UUID>, JpaSpecificationExecutor<Prontuario> {
    boolean existsByConsulta(Consulta consulta);
    @EntityGraph(attributePaths = {"consulta","paciente","paciente.usuario","medico","medico.usuario"})
    Optional<Prontuario> findByConsultaId(UUID consultaId);
}
