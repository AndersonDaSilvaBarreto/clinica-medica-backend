package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import java.time.LocalDate;
import java.util.UUID;

public interface UsuarioApi {
    Usuario criarFuncionario(String nome,
                                   Email email,
                                   Genero genero,
                                   Cpf cpf,
                                   Perfil perfil,
                                   LocalDate dataNascimento,
                                   String telefone);

    UsuarioResumo buscarPorId(UUID id);
    Usuario buscarUsuarioPorId(UUID id);

    UsuarioResumo buscarPorEmail(Email email);

    UsuarioResumo trocarTelefone(UUID usuarioId, String telefone);

    UsuarioResumo trocarDataNascimento(UUID usuarioId, LocalDate dataNascimento);
    boolean existePorCpf(Cpf cpf);
    void apagarCache(Usuario usuario);
}
