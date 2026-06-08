package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.infra.security.TokenService;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginResponse;
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

    public LoginResponse execute(LoginDto dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha()
        );
        var auth = authenticationManager.authenticate(usernamePassword);
        var usuarioAutenticado = (UsuarioAutenticado) auth.getPrincipal();

        assert usuarioAutenticado != null;
        String accessToken = tokenService.generateToken(usuarioAutenticado.getUsuario());
        String refreshToken = tokenService.generateRefreshToken();
        redisService.salvar(
                "refresh:"+refreshToken,
                refreshToken,
                Duration.ofDays(7)
        );
        return new LoginResponse(
                accessToken,
                refreshToken
        );

    }
}
