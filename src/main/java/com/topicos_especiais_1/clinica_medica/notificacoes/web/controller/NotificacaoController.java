package com.topicos_especiais_1.clinica_medica.notificacoes.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase.BuscaPaginadaNotificacaoUseCase;
import com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase.NotificacaoLidaUseCase;
import com.topicos_especiais_1.clinica_medica.notificacoes.web.dto.NotificacaoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {
    private final BuscaPaginadaNotificacaoUseCase buscaPaginadaNotificacaoUseCase;
    private final NotificacaoLidaUseCase notificacaoLidaUseCase;
    @GetMapping
    public ResponseEntity<PaginacaoResponse<NotificacaoResponse>> buscaPaginada(
            @RequestParam(required = false)UUID cursor,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestParam(required = false) Boolean lida,
            @RequestParam(required = false,defaultValue = "10") int limit
            ) {
        var response = buscaPaginadaNotificacaoUseCase.execute(
                cursor,usuarioAutenticado.usuario(),
                lida,limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PatchMapping("/{notificacaoId}")
    public ResponseEntity<Void> notificacaolida(
        @PathVariable UUID notificacaoId,
        @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        notificacaoLidaUseCase.execute(notificacaoId,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

}
