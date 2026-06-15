package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Genero;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;


import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        Perfil perfil,
        String telefone,
        boolean ativo,
        Genero genero,
        String cpf
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome().toString(),
                usuario.getEmail().toString(),
                usuario.getPerfil(),
                usuario.getTelefone()
                        .map(Telefone::toString)
                        .orElse(null),
                usuario.getAtivo(),
                usuario.getGenero(),
                usuario.getCpf().toString()
        );
}}
