package com.topicos_especiais_1.clinica_medica.atendimento.web.controller;

import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.AdicionarNaFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AdicionarFilaRequest;
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
@RequestMapping("/fila")
@RequiredArgsConstructor
public class AtendimentoController {
    private final AdicionarNaFilaUseCase adicionarNaFilaUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<Void> adicionarPacienteNaFila(
            @RequestBody @Valid AdicionarFilaRequest request
            ) {
        adicionarNaFilaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }


}
