package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPacienteRepository extends JpaRepository<Paciente, UUID> {
    @EntityGraph(attributePaths = "usuario")
    Optional<Paciente> findByUsuarioId(UUID usuarioId);


}
