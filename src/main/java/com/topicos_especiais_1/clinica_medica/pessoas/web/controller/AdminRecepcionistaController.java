package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.*;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtivoRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarRecepcionistaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarRecepcionistaRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.RecepcionistaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/recepcionistas")
@RequiredArgsConstructor
public class AdminRecepcionistaController {
    private final CriarRecepcionistaUseCase criarRecepcionistaUseCase;
    private final BuscaRecepcionistaPaginadoUseCase buscaRecepcionistaPaginadoUseCase;
    private final BuscarRecepcionistaPorIdUseCase buscarRecepcionistaPorIdUseCase;
    private final AtualizarRecepcionistaUseCase atualizarRecepcionistaUseCase;
    private final AtivoRecepcionistaUseCase ativoRecepcionistaUseCase;

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
    @GetMapping("/{recepcionistaId}")
    public ResponseEntity<RecepcionistaResponse> buscarPorId(
            @PathVariable UUID recepcionistaId
    ) {
        var response = buscarRecepcionistaPorIdUseCase.execute(recepcionistaId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PatchMapping("/{recepcionistaId}")
    public ResponseEntity<Void> atualizarRecepcionista(
            @PathVariable UUID recepcionistaId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestBody @Valid AtualizarRecepcionistaRequest request
            ) {
            atualizarRecepcionistaUseCase.execute(recepcionistaId,usuarioAutenticado,request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
    }
    @DeleteMapping("/{recepcionistaId}")
    public ResponseEntity<Void> ativoRecepcionista(
            @PathVariable UUID recepcionistaId,
            @RequestBody @Valid AtivoRequest request
            ) {
        ativoRecepcionistaUseCase.execute(recepcionistaId,request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
