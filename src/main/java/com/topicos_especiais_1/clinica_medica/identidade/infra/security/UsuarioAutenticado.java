package com.topicos_especiais_1.clinica_medica.identidade.infra.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;

public record UsuarioAutenticado(Usuario usuario) implements UserDetails {

    public UsuarioAutenticado(Usuario usuario) {
        this.usuario = Objects.requireNonNull(usuario);
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario().getPerfil().name()));
    }

    @Override
    public String getPassword() {
        String senhaHash = usuario().getSenha().toString();
        System.out.println("====== [PASSO 2] Spring Security lendo getPassword(): [" + senhaHash + "]");
        return senhaHash;
    }

    @Override
    public @NonNull String getUsername() {
        return usuario().getEmail().toString();
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
        return usuario().getAtivo();
    }


}
