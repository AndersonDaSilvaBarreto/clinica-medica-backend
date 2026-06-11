package com.topicos_especiais_1.clinica_medica.identidade.domain.repository;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Usuario atualizar(Usuario usuario);
    Usuario buscarPorId(UUID id);
    Usuario buscarPorEmail(Email id);
    boolean existePorEmail(Email email);
    boolean existePorid(UUID id);
    void deletarPorId(UUID id);
}
