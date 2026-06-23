package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.CriarRecepcionistaUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarRecepcionistaRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/recepcionistas")
@RequiredArgsConstructor
public class AdminRecepcionistaController {
    private final CriarRecepcionistaUseCase criarRecepcionistaUseCase;

    @PostMapping
    public ResponseEntity<Void> criarRecepcionista(
            @RequestBody @Valid CriarRecepcionistaRequest request
            ) {
        criarRecepcionistaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
}
