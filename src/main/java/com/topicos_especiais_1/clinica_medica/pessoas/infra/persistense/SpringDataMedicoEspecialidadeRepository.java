package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.MedicoEspecialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.MedicoEspecialidadeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMedicoEspecialidadeRepository extends JpaRepository<MedicoEspecialidade, MedicoEspecialidadeId> {
}
