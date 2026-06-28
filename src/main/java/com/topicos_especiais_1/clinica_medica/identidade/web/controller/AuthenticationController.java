package com.topicos_especiais_1.clinica_medica.identidade.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.*;
import com.topicos_especiais_1.clinica_medica.identidade.web.CookieService;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ComecarRegistroPacienteUseCase comecarRegistroPacienteUseCase;
    private final VerificarRegistroUseCase verificarRegistroUseCase;
    private final LoginUseCase loginUseCase;
    private final CookieService cookieService;
    private final RefreshUseCase refreshUseCase;
    private final EsqueciSenhaUseCase esqueciSenhaUseCase;
    private final EsqueciSenhaVerificarCodigoUseCase esqueciSenhaVerificarCodigoUseCase;
    private final EsqueciSenhaTrocarSenhaUseCase esqueciSenhaTrocarSenhaUseCase;
    @PostMapping("/register/start")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto dto) {
        comecarRegistroPacienteUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @PostMapping("/register/verify")
    public ResponseEntity<Void> verificar(
           @RequestBody @Valid VerificacaoRegistroDto dto
    ) {
        verificarRegistroUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@RequestBody @Valid LoginDto data) {
        var loginResponse = loginUseCase.execute(data);
        var cookies = cookieService.gerarCookiesAutenticacao(
                loginResponse.accessToken(),
                loginResponse.refreshToken()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookies.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, cookies.refreshCookie().toString())
                .body(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticateResponse> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody RefreshTokenRequest request) {
                String refreshTokenValue = refreshToken;
                if(refreshToken == null && request.refreshToken() != null) refreshTokenValue = request.refreshToken();
                var refreshResponse = refreshUseCase.execute(refreshTokenValue);
                var cookies = cookieService.gerarCookiesAutenticacao(
                        refreshResponse.accessToken(),
                        refreshResponse.refreshToken()
                );
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookies.accessCookie().toString())
                .header(HttpHeaders.SET_COOKIE, cookies.refreshCookie().toString())
                .body(refreshResponse);

    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<Void> esqueciSenha(
            @RequestBody @Valid EsqueciSenhaRequest request
    ) {
        esqueciSenhaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
    @PostMapping("/esqueci-minha-senha-verificar")
    public ResponseEntity<EsqueciSenhaVerificadoResponse> esqueciSenhaVerificar(
            @RequestBody @Valid EsqueciSenhaVerificarCodigoRequest request
    ) {
        var response = esqueciSenhaVerificarCodigoUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PostMapping("/esqueci-minha-senha-trocar-senha")
    public ResponseEntity<Void> trocarSenha(
            @RequestBody @Valid EsqueciSenhaTrocarSenhaRequest request
    ) {
        esqueciSenhaTrocarSenhaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }


}
