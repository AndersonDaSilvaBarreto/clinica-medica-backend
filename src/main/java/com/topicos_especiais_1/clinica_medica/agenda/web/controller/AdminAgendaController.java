package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.AtualizarAgendaUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AtualizarAgendaRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/medicos/{medicoId}/agenda")
@RequiredArgsConstructor
public class AdminAgendaController {
    private final AtualizarAgendaUseCase atualizarAgendaUseCase;
    @PutMapping
    public ResponseEntity<Void> atualizarMedicoAgenda(
            @PathVariable UUID medicoId,
            @RequestBody @Valid AtualizarAgendaRequest request
            ) {
        atualizarAgendaUseCase.execute(medicoId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
