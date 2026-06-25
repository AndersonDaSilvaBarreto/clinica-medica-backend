package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.*;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;
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
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Embedded
    private Telefone telefone;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @Embedded
    private Cpf cpf;

    @Embedded
    private DataNascimento dataNascimento;


    private Usuario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            @NonNull Genero genero,
            @NonNull Cpf cpf,
            Perfil perfil,
            DataNascimento dataNascimento,
            Telefone telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.genero = genero;
        this.cpf = cpf;
        this.perfil = perfil != null ? perfil : Perfil.PACIENTE;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.ativo = true;
    }

    public static Usuario createPaciente(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            @NonNull Genero genero,
            @NonNull Cpf cpf,
            DataNascimento dataNascimento,
            Telefone telefone) {
        return new Usuario(nome, email, senha, genero, cpf , Perfil.PACIENTE, dataNascimento, telefone);
    }

    public static Usuario createFuncionario(
            @NonNull Nome nome,
            @NonNull Email email,
            @NonNull Senha senha,
            @NonNull Genero genero,
            @NonNull Cpf cpf,
            @NonNull Perfil perfil,
            DataNascimento dataNascimento,
            Telefone telefone) {
        return new Usuario(nome, email, senha,genero, cpf, perfil, dataNascimento, telefone);
    }

    public void mudarNome(Nome nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    public void mudarEmail(Email email) {
        this.email = Objects.requireNonNull(email);
    }

    public void mudarTelefone(Telefone telefone) {
        this.telefone = Objects.requireNonNull(telefone);
    }

    public void mudarSenha(Senha senha) {
        this.senha = Objects.requireNonNull(senha);
    }

    public void mudarPerfil(Perfil perfil) {
        this.perfil = Objects.requireNonNull(perfil);
    }

    public void mudarAtivo(Boolean ativo) {
        this.ativo = Objects.requireNonNull(ativo);
    }

    public void mudarDataNascimento(DataNascimento dataNascimento) {
        this.dataNascimento = Objects.requireNonNull(dataNascimento);
    }
    public Optional<Telefone> getTelefone() {
        return Optional.ofNullable(telefone);
    }
    public Optional<DataNascimento> getDataNascimento() {return Optional.ofNullable(dataNascimento);}


}
