package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscaMedicoPaginadoUseUse;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
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
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final BuscaMedicoPaginadoUseUse buscaMedicoPaginadoUseUse;

    @GetMapping
    public ResponseEntity<PaginacaoResponse<MedicoResponse>> buscaPaginada(
            @RequestParam(name = "cursor", required = false)UUID cursor,
            @RequestParam(name = "busca",required = false) String busca,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit
            ) {
        var response = buscaMedicoPaginadoUseUse.execute(cursor,busca,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
