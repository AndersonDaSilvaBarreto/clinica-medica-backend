package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.AtualizarEspecialidadeUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.CriarEspecialidadeUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.DeletarEspecialidadePorIdUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarEspecialidadeRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarEspecialidadeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/especialidades")
@RequiredArgsConstructor
public class AdminEspecialidadeController {
    private final CriarEspecialidadeUseCase criarEspecialidadeUseCase;
    private final AtualizarEspecialidadeUseCase atualizarEspecialidadeUseCase;
    private final DeletarEspecialidadePorIdUseCase deletarEspecialidadePorIdUseCase;
    @PostMapping
    public ResponseEntity<Void> criarEspecialidade(
            @RequestBody @Valid CriarEspecialidadeRequest request
            ) {
        criarEspecialidadeUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    @PatchMapping("/{especialidadeId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> atualizarEspecialidade(
            @PathVariable UUID especialidadeId,
            @RequestBody @Valid AtualizarEspecialidadeRequest request
            ) {
        atualizarEspecialidadeUseCase.execute(especialidadeId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @DeleteMapping("/{especialidadeId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletarEspecialidadePorId(
        @PathVariable UUID especialidadeId
    ) {
        deletarEspecialidadePorIdUseCase.execute(especialidadeId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

}
