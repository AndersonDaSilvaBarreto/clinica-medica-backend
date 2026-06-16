package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(Email email);
    Optional<Usuario> findByCpf(Cpf cpf);
    boolean existsByEmail(Email email);
    boolean existsByCpf(Cpf cpf);
}
