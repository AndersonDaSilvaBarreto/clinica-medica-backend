package com.topicos_especiais_1.clinica_medica.identidade.infra.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.ComecarRegistroPacienteUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto.AuthenticationDto;
import com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto.RegisterDto;
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
    private final ComecarRegistroPacienteUseCase useCase;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/register/start")
    public ResponseEntity<Map<String,String>> register(@RequestBody @Valid RegisterDto dto) {
        String codigo = useCase.execute(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("codigo", codigo));
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            return ResponseEntity.ok().build();
    }
}
