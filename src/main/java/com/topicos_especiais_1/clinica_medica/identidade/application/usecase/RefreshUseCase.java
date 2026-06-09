package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosRefreshToken;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.CodigoExpiradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioNaoEncontradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.TokenService;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.AuthenticateResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshUseCase {
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final RedisService redisService;

    @Transactional
    public AuthenticateResponse execute(String oldRefreshToken) {
        if (oldRefreshToken == null) {
            throw new CodigoExpiradoException(CodigoExpiradoException.REFRESH_TOKEN_EXPIRADO);
        }
        String chaveAntiga = RedisService.REFRESH_KEY + oldRefreshToken;
        var dados = redisService.buscar(
                chaveAntiga,
                DadosRefreshToken.class
        ).orElseThrow(
                () -> new CodigoExpiradoException(CodigoExpiradoException.CODIGO_EXPIRADO)
        );
        Usuario usuario = usuarioRepository.buscarPorId(dados.id())
                .orElseThrow(() -> UsuarioNaoEncontradoException.porId(dados.id()));
        String accessToken = tokenService.generateToken(usuario);
        String refresToken = tokenService.generateRefreshToken();
        String chaveNova = RedisService.REFRESH_KEY + refresToken;
        redisService.deletar(RedisService.REFRESH_KEY + oldRefreshToken);
        redisService.salvar(chaveNova, new DadosRefreshToken(usuario.getId()), Duration.ofDays(7));
        
        return new AuthenticateResponse(
                accessToken,
                refresToken
        );
    }
}
