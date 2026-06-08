package com.topicos_especiais_1.clinica_medica.identidade.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.ComecarRegistroPacienteUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.LoginUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.VerificarRegistroUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.web.CookieService;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.LoginResponse;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.RegisterDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.VerificacaoRegistroDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ComecarRegistroPacienteUseCase comecarRegistroPacienteUseCase;
    private final VerificarRegistroUseCase verificarRegistroUseCase;
    private final LoginUseCase loginUseCase;
    private final CookieService cookieService;

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
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDto data) {
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
}
