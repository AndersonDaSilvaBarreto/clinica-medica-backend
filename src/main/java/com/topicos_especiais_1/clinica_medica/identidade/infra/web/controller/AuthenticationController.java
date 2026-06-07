package com.topicos_especiais_1.clinica_medica.identidade.infra.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto.AuthenticationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            return ResponseEntity.ok().build();
    }
}
