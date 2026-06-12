package com.topicos_especiais_1.clinica_medica.identidade.domain.repository;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.UUID;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Usuario atualizar(Usuario usuario);
    Usuario buscarPorId(UUID id);
    Usuario buscarPorEmail(Email id);
    Usuario buscarPorCpf(Cpf cpf);
    boolean existePorEmail(Email email);
    boolean existePorid(UUID id);
    boolean existePorCpf(Cpf cpf);
    void deletar(Usuario id);
}
