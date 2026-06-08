package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.Perfil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Optional;

@Table(name = "usuarios")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Usuario  {
    @EmbeddedId
    private UsuarioId id;

    @Embedded
    private Nome nome;

    @Embedded
    private Email email;

    @Embedded
    private Senha senha;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Embedded
    private Telefone telefone;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Instant dataCriacao;

    private Usuario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            Perfil perfil) {
        this.id = UsuarioId.generate();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil != null ? perfil : Perfil.PACIENTE;
        this.ativo = true;
        this.dataCriacao = Instant.now();
    }

    public static Usuario createPaciente(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha) {
        return new Usuario(nome, email, senha, Perfil.PACIENTE);
    }

    public static Usuario createFuncionario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            @NonNull Perfil perfil) {
        return new Usuario(nome, email, senha, perfil);
    }

    public void mudarNome(@NonNull Nome nome) {this.nome = nome;}
    public void mudarEmail(@NonNull Email email) {this.email = email;}
    public void mudarTelefone(@NonNull Telefone telefone) {
        this.telefone = telefone;
    }
    public void mudarSenha(@NonNull Senha senha) {this.senha = senha;}
    public void mudarPerfil(@NonNull Perfil perfil) {this.perfil = perfil;}
    public void mudarAtivo(@NonNull Boolean ativo) {this.ativo = ativo;}
    public Optional<Telefone> getTelefone() {
        return Optional.ofNullable(telefone);
    }


}
