package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataEspecialidadeRepository extends JpaRepository<Especialidade, UUID> {
    boolean existsByNome(Nome nome);
}
