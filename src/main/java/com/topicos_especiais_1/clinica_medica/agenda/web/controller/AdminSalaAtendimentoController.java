package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.AtualizarSalaAtendimentoUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.CriarSalaAtendimentoUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AtualizarSalaAtendimentoRequest;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.CriarSalaAtendimentoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/salas")
@RequiredArgsConstructor
public class AdminSalaAtendimentoController {
    private final CriarSalaAtendimentoUseCase criarSalaAtendimentoUseCase;
    private final AtualizarSalaAtendimentoUseCase atualizarSalaAtendimentoUseCase;
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> criarSalaAtendimento(
            @RequestBody @Valid CriarSalaAtendimentoRequest criarSalaAtendimentoRequest
            ) {
        criarSalaAtendimentoUseCase.execute(criarSalaAtendimentoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    @PatchMapping("/{salaId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> atualizarSalaAtendimento(
            @PathVariable UUID salaId,
            @RequestBody @Valid AtualizarSalaAtendimentoRequest request
            ) {
        atualizarSalaAtendimentoUseCase.execute(salaId,request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
