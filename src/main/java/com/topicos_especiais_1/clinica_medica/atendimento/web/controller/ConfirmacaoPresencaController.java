package com.topicos_especiais_1.clinica_medica.atendimento.web.controller;

import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.RegistrarConfirmacaoPresencaUseCase;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/consultas/{consultaId}/confirmacao-presenca")
@RequiredArgsConstructor
public class ConfirmacaoPresencaController {
    private final RegistrarConfirmacaoPresencaUseCase registrarConfirmacaoPresencaUseCase;

    @PostMapping
    @PreAuthorize("hasRole('RECEPCIONISTA')")
    public ResponseEntity<Void> registrar(
            @PathVariable UUID consultaId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        registrarConfirmacaoPresencaUseCase.execute(consultaId,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);

    }
}
