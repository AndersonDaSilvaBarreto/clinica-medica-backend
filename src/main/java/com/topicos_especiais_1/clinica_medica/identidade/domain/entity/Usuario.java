package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

@Table(name = "usuarios")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Instant dataCriacao;

    private Usuario (
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            Perfil perfil,
            Telefone telefone){
            this.id = UsuarioId.generate();
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.perfil = perfil != null ? perfil : Perfil.PACIENTE;
            this.telefone = telefone;
            this.ativo = true;
            this.dataCriacao = Instant.now();
    }
    public static Usuario createPaciente(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            Telefone telefone) {
        return new Usuario(nome, email, senha, Perfil.PACIENTE, telefone);
    }

    public static Usuario createFuncionario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            @NonNull Perfil perfil,
            Telefone telefone) {
        return new Usuario(nome, email, senha, perfil, telefone);
    }

    public Optional<Telefone> getTelefone() {
        return Optional.ofNullable(telefone);
    }



}
