package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscaRecepcionistaPaginadoUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.CriarRecepcionistaUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarRecepcionistaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.RecepcionistaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/recepcionistas")
@RequiredArgsConstructor
public class AdminRecepcionistaController {
    private final CriarRecepcionistaUseCase criarRecepcionistaUseCase;
    private final BuscaRecepcionistaPaginadoUseCase buscaRecepcionistaPaginadoUseCase;

    @PostMapping
    public ResponseEntity<Void> criarRecepcionista(
            @RequestBody @Valid CriarRecepcionistaRequest request
            ) {
        criarRecepcionistaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    @GetMapping
    public ResponseEntity<PaginacaoResponse<RecepcionistaResponse>> buscaRecepcionistaPaginado(
            @RequestParam(required = false) UUID cursor,
            @RequestParam(required = false) String busca,
            @RequestParam(defaultValue = "10") int limit
            ) {
        var response = buscaRecepcionistaPaginadoUseCase.execute(cursor,busca,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
