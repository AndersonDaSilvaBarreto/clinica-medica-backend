package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.BuscaPaginadaSalaAtendimentoUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.SalaAtendimentoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalaAtendimentoController {
    private final BuscaPaginadaSalaAtendimentoUseCase buscaPaginadaSalaAtendimentoUseCase;

    @GetMapping
    public ResponseEntity<PaginacaoResponse<SalaAtendimentoResponse>> buscaPaginada(
            @RequestParam(required = false)UUID cursor,
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) Boolean ativa,
            @RequestParam(required = false, defaultValue = "10") int limit
            ) {
        var response = buscaPaginadaSalaAtendimentoUseCase.execute(cursor,busca,ativa,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
