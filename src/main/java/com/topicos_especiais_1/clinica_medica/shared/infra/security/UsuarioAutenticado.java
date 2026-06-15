package com.topicos_especiais_1.clinica_medica.shared.infra.security;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.*;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class UsuarioAutenticado implements UserDetails {

    private final UUID id;
    private final String nome;
    private final Email email;
    private final Perfil perfil;
    private final String telefone;
    private final String genero;
    private final Cpf cpf;
    private final LocalDate dataNascimento;
    private final boolean ativo;

    private final String senhaHash;

    public UsuarioAutenticado(UUID id, String nome, Email email, Perfil perfil, String telefone, String genero, Cpf cpf, LocalDate dataNascimento, boolean ativo, String senhaHash) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.telefone = telefone;
        this.genero = genero;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.ativo = ativo;
        this.senhaHash = senhaHash;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public @NonNull String getUsername() {
        return email.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    public Optional<String> getTelefone() {
        return Optional.ofNullable(telefone);
    }
    public Optional<LocalDate> getDataNascimento() {
        return Optional.ofNullable(dataNascimento);
    }

}
