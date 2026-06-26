package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.CriarBloqueioAgendaUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.DeletarBloqueioAgendaUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.BuscarBloqueioAgendaPaginado;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.CriarBloqueioAgendaRequest;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.BloqueioAgendaResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import java.time.LocalDate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/bloqueios")
@RequiredArgsConstructor
public class AdminBloqueioAgendaController {
    private final CriarBloqueioAgendaUseCase criarBloqueioAgendaUseCase;
    private final DeletarBloqueioAgendaUseCase deletarBloqueioAgendaUseCase;
    private final BuscarBloqueioAgendaPaginado buscarBloqueioAgendaPaginado;
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> criar(
            @RequestBody @Valid CriarBloqueioAgendaRequest request
            ) {
        criarBloqueioAgendaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PaginacaoResponse<BloqueioAgendaResponse>> listar(
            @RequestParam(value = "cursor", required = false) UUID cursor,
            @RequestParam(value = "medicoId", required = false) UUID medicoId,
            @RequestParam(value = "dataInicio", required = false) LocalDate dataInicio,
            @RequestParam(value = "dataFim", required = false) LocalDate dataFim,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        var response = buscarBloqueioAgendaPaginado.execute(cursor, medicoId, dataInicio, dataFim, limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @DeleteMapping("/{bloqueioId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID bloqueioId
            ) {
        deletarBloqueioAgendaUseCase.execute(bloqueioId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

}
