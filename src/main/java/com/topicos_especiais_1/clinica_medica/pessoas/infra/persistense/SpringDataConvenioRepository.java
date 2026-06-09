package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataConvenioRepository extends JpaRepository<Convenio, UUID> {
    
}
