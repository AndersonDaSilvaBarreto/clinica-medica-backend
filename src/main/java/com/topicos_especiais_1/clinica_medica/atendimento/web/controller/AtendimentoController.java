package com.topicos_especiais_1.clinica_medica.atendimento.web.controller;

import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.AdicionarNaFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AdicionarFilaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fila")
@RequiredArgsConstructor
public class AtendimentoController {
    private final AdicionarNaFilaUseCase adicionarNaFilaUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<Void> adicionarPacienteNaFila(
            @RequestBody @Valid AdicionarFilaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        adicionarNaFilaUseCase.execute(request,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }


}
