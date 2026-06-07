package com.topicos_especiais_1.clinica_medica.identidade.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, UsuarioId> {
    Optional<Usuario> findByEmail(Email email);
    boolean existsByEmail(Email email);
}
