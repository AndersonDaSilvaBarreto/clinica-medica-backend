package com.topicos_especiais_1.clinica_medica.identidade.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.BuscarUsuarioPorIDUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final BuscarUsuarioPorIDUseCase useCase;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> usuarioPorId(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(useCase.execute(usuarioAutenticado.getId()));

    }
}
