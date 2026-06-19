package com.topicos_especiais_1.clinica_medica.identidade.infra.security;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repository;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email) {
        Usuario usuario = repository.buscarPorEmail(Email.of(email));
        return new UsuarioAutenticado(
               usuario
        );
    }
}
