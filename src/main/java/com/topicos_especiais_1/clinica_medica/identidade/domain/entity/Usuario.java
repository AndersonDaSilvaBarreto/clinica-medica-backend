package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Table(name = "usuarios")
@Entity
@Getter

public class Usuario {
    @EmbeddedId
    private UsuarioId id;

    @Embedded
    private Nome nome;

    @Embedded
    private Email email;

    @Embedded
    private Senha senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Embedded
    private Telefone telefone;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "data_criacao", nullable = false)
    private Instant dataCriacao;



}
