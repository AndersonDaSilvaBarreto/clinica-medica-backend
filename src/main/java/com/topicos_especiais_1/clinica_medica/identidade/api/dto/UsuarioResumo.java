package com.topicos_especiais_1.clinica_medica.identidade.api.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Telefone;

import java.util.Optional;
import java.util.UUID;

public record UsuarioResumo(
        UUID id,
        Nome nome,
        Email email,
        Perfil perfil,
        Optional<Telefone> telefone,
        boolean ativo

) {
    public static UsuarioResumo ofUsuario(Usuario usuario) {
        return new UsuarioResumo(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getTelefone(),
                usuario.getAtivo()
        );
    }
}
