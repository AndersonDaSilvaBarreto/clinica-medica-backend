package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscaEspecialidadesPaginadaUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.EspecialidadeResponse;
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
@RequestMapping("/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {
    private final BuscaEspecialidadesPaginadaUseCase buscaEspecialidadesPaginadaUseCase;

    @GetMapping
    public ResponseEntity<PaginacaoResponse<EspecialidadeResponse>> buscaPaginada(
            @RequestParam(value = "cursor", required = false)UUID cursor,
            @RequestParam(value = "busca",required = false) String busca,
            @RequestParam(value = "limit", defaultValue = "10") int limit
            ) {
            var response = buscaEspecialidadesPaginadaUseCase.execute(cursor,busca,limit);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
    }

}
