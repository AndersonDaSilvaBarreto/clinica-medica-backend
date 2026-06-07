package com.topicos_especiais_1.clinica_medica.identidade.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.TokenInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    private static final Duration EXPIRATION = Duration.ofMinutes(15);

    public String generateToken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("clinica-medica-api")
                    .withSubject(usuario.getId().toString())
                    .withClaim("role", usuario.getPerfil().name())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw TokenInvalidoException.criacaoErro();
        }
    }
    public DecodedJWT getDecodedToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("clinica-medica-api")
                    .build()
                    .verify(token);
        }  catch (JWTVerificationException exception) {
            throw TokenInvalidoException.validacaoErro();
        }
    }
    public String generateRefreshToken() {
        return UuidCreator.getRandomBased().toString();
    }
    private Instant genExpirationDate() {
        return Instant.now().plus(EXPIRATION);
    }
}
