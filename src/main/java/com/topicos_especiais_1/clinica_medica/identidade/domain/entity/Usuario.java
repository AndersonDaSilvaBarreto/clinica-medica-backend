package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Optional;

@Table(name = "usuarios")
@Entity
@NoArgsConstructor
@Getter
public class Usuario extends BaseEntity implements Serializable {


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

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @Embedded
    private CPF cpf;


    private Usuario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil != null ? perfil : Perfil.PACIENTE;
        this.ativo = true;
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
