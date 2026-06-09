package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPacienteRepository extends JpaRepository<Paciente, UUID> {
    Optional<Paciente> findByCpf(CPF cpf);
}
