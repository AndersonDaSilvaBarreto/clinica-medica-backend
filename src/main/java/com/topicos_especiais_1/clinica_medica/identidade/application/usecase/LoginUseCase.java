package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosRefreshToken;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.TokenService;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.AuthenticateResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RedisService redisService;

    public AuthenticateResponse execute(LoginDto dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha()
        );
        var auth = authenticationManager.authenticate(usernamePassword);
        var usuarioAutenticado = (UsuarioAutenticado) auth.getPrincipal();

        assert usuarioAutenticado != null;
        Usuario usuario = usuarioAutenticado.getUsuario();
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
