package com.topicos_especiais_1.clinica_medica.identidade.infra.security;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
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
                usuario.getId(),
                usuario.getNome().toString(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getTelefone().map(Telefone::toString).orElse(null),
                usuario.getGenero().toString(),
                usuario.getCpf(),
                usuario.getDataNascimento().map(DataNascimento::getValue).orElse(null),
                usuario.getAtivo(),
                usuario.getSenha().toString()
        );
    }
}
