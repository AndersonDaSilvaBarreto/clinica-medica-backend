package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.BuscarBloqueioAgendaPaginado;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.BloqueioAgendaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/bloqueios")
@RequiredArgsConstructor
public class BloqueiosAgendaController {
    private final BuscarBloqueioAgendaPaginado buscarBloqueioAgendaPaginado;

    @GetMapping
    public ResponseEntity<PaginacaoResponse<BloqueioAgendaResponse>> buscaPaginada(
            @RequestParam(required = false) UUID cursor,
            @RequestParam(required = false) UUID medicoId,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(required = false,defaultValue = "10") int limit
            ) {
        var response = buscarBloqueioAgendaPaginado.execute(
                cursor,
                medicoId,
                dataInicio,
                dataFim,
                limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
