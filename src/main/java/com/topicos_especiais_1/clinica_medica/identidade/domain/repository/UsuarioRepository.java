package com.topicos_especiais_1.clinica_medica.identidade.domain.repository;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Usuario atualizar(Usuario usuario);
    Optional<Usuario> buscarPorId(UsuarioId usuarioId);
    Optional<Usuario> buscarPorEmail(Email email);
    boolean existePorEmail(Email email);
    boolean existePorid(UsuarioId usuarioId);
    void deletarPorId(UsuarioId id);
}
