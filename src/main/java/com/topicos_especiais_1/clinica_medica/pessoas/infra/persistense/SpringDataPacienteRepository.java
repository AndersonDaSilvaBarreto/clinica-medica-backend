package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPacienteRepository extends JpaRepository<Paciente, UUID>, JpaSpecificationExecutor<Paciente> {
    @EntityGraph(attributePaths = "usuario")
    Optional<Paciente> findByUsuarioId(UUID usuarioId);


}
