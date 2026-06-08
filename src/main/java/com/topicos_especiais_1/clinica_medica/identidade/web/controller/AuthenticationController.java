package com.topicos_especiais_1.clinica_medica.identidade.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.ComecarRegistroPacienteUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.VerificarRegistroUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.AuthenticationDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.RegisterDto;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.VerificacaoRegistroDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ComecarRegistroPacienteUseCase comecarRegistroPacienteUseCase;
    private final VerificarRegistroUseCase verificarRegistroUseCase;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register/start")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterDto dto) {
        String codigo = comecarRegistroPacienteUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("codigo", codigo));
    }
    @PostMapping("/register/verify")
    public ResponseEntity<Void> verificar(
           @RequestBody @Valid VerificacaoRegistroDto dto
    ) {
        verificarRegistroUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }
}
