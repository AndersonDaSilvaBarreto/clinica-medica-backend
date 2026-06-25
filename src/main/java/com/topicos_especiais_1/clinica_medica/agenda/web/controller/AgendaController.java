package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.BuscarAgendasPorMedicoUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AgendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/medicos/{medicoId}/agenda")
@RequiredArgsConstructor
public class AgendaController {
    private final BuscarAgendasPorMedicoUseCase buscarAgendasPorMedicoUseCase;

    @GetMapping
    public ResponseEntity<AgendaResponse> buscarAgendaPorMedico(
            @PathVariable UUID medicoId
            ) {
        var response = buscarAgendasPorMedicoUseCase.execute(medicoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
