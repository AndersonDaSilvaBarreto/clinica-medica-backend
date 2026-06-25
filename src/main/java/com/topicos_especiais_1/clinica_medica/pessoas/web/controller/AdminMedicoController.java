package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.CriarMedicoUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.AtivoMedicoUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtivoRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarMedicoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/medicos")
@RequiredArgsConstructor
public class AdminMedicoController {
    private final CriarMedicoUseCase criarMedicoUseCase;
    private final AtivoMedicoUseCase ativoMedicoUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> criarMedico(
            @RequestBody @Valid CriarMedicoRequest request
            ) {
            criarMedicoUseCase.execute(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(null);
    }
    @PatchMapping("/{medicoId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> ativarEDesativarMedico(
            @PathVariable UUID medicoId,
            @RequestBody @Valid AtivoRequest ativoRequest
            ) {
            ativoMedicoUseCase.execute(medicoId,ativoRequest);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);

}}
