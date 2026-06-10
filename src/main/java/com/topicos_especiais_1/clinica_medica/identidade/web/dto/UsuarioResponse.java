package com.topicos_especiais_1.clinica_medica.identidade.web.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Telefone;

import java.time.Instant;

public record UsuarioResponse(
        String id,
        String nome,
        String email,
        String perfil,
        String telefone,
        Boolean ativo,
        Instant dataCriacao
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId().toString(), // Assumindo que seu BaseEntity expõe o ID (UUID ou Long)
                usuario.getNome().getValue(),   // Extrai a String do Value Object Nome
                usuario.getEmail().getValue(),  // Extrai a String do Value Object Email
                usuario.getPerfil().name(),     // Converte o Enum Perfil para String
                usuario.getTelefone()
                        .map(Telefone::getValue)  // Como o telefone é um Optional, mapeia se existir
                        .orElse(null),           // Retorna null se não estiver preenchido
                usuario.getAtivo(),
                usuario.getDataCriacao()
        );
}}
