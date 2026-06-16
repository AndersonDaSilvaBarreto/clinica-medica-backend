package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.CriarEspecialidadeUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarEspecialidadeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {
    private final CriarEspecialidadeUseCase criarEspecialidadeUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> criarEspecialidade(
            @RequestBody @Valid CriarEspecialidadeRequest request
            ) {
        criarEspecialidadeUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }


}
