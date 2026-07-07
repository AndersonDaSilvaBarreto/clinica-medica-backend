package com.topicos_especiais_1.clinica_medica.identidade.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.BuscarUsuarioPorIDUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.MudarEmailUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.application.usecase.MudarEmailVerificarUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.MudarEmailRequest;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.MudarEmailVerificarRequest;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final BuscarUsuarioPorIDUseCase useCase;
    private final MudarEmailUseCase mudarEmailUseCase;
    private final MudarEmailVerificarUseCase mudarEmailVerificarUseCase;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> usuarioPorId(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(useCase.execute(usuarioAutenticado.usuario().getId()));

    }
    @PatchMapping("mudar-email")
    public ResponseEntity<Void> mudarEmail(
            @RequestBody @Valid MudarEmailRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        mudarEmailUseCase.execute(request,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
    @PatchMapping("mudar-email-verificar")
    public ResponseEntity<Void> mudarEmailVerificar(
            @RequestBody @Valid MudarEmailVerificarRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        mudarEmailVerificarUseCase.execute(request,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
