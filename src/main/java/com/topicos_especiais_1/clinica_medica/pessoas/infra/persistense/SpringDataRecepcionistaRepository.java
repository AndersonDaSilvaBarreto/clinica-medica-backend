package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataRecepcionistaRepository extends JpaRepository<Recepcionista, UUID> {
    Optional<Recepcionista> findByUsuarioCpf(Cpf usuarioCpf);

}
