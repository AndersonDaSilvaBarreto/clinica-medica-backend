package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosRefreshToken;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.TokenService;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.AuthenticateResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RedisService redisService;
    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    public AuthenticateResponse execute(LoginDto dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha()
        );
        var auth = authenticationManager.authenticate(usernamePassword);
        var usuarioAutenticado = (UsuarioAutenticado) auth.getPrincipal();


        Usuario usuario = repository.buscarPorId(Objects.requireNonNull(usuarioAutenticado).getId());
        String accessToken = tokenService.generateToken(usuario);
        String refreshToken = tokenService.generateRefreshToken();
        DadosRefreshToken dadosRefreshToken = new DadosRefreshToken(
                usuario.getId()
        );
        redisService.salvar(
                RedisService.REFRESH_KEY+refreshToken,
                dadosRefreshToken,
                Duration.ofDays(7)
        );
        return new AuthenticateResponse(
                accessToken,
                refreshToken
        );

    }
}
