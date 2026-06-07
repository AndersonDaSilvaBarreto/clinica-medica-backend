package com.topicos_especiais_1.clinica_medica.identidade.infra.security;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UsuarioAutenticado implements UserDetails {
    private final Usuario usuario;
    public UsuarioAutenticado(@NonNull Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getUsuario().getPerfil().name()));
    }

    @Override
    public String getPassword() {
        return getUsuario().getSenha().getValue();
    }

    @Override
    public @NonNull String getUsername() {
        return getUsuario().getEmail().toString();
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
        return getUsuario().getAtivo();
    }

}
