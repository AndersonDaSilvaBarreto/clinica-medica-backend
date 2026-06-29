package com.topicos_especiais_1.clinica_medica.pagamentos.web.controller;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.ConsultarPagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase.ConsultarPagamentoUseCase;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase.CriarPagamentoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final CriarPagamentoUseCase criarPagamentoUseCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;

    @PostMapping
    public ResponseEntity<PagamentoResponse> criarPagamento(
            @Valid @RequestBody CriarPagamentoRequest request
    ) {

        PagamentoResponse response =
                criarPagamentoUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultarPagamentoResponse> consultarPagamento(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                consultarPagamentoUseCase.execute(id)
        );
    }
}
