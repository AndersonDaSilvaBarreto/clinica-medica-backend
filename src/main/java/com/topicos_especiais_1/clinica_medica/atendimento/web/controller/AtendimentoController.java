package com.topicos_especiais_1.clinica_medica.atendimento.web.controller;

import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.AdicionarNaFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.AtualizarStatusFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.BuscaPaginadaFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.PacienteChamadoFilaUseCase;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AdicionarFilaRequest;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AtualizarStatusFilaRequest;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.FilaAtendimentoResponse;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponseCursorInteger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/fila")
@RequiredArgsConstructor
public class AtendimentoController {
    private final AdicionarNaFilaUseCase adicionarNaFilaUseCase;
    private final BuscaPaginadaFilaUseCase buscaPaginadaFilaUseCase;
    private final PacienteChamadoFilaUseCase pacienteChamadoFilaUseCase;
    private final AtualizarStatusFilaUseCase atualizarStatusFilaUseCase;
    @PostMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<Void> adicionarPacienteNaFila(
            @RequestBody @Valid AdicionarFilaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        adicionarNaFilaUseCase.execute(request,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping
    public ResponseEntity<PaginacaoResponseCursorInteger<FilaAtendimentoResponse>> buscaPaginada(
            @RequestParam(required = false) UUID medicoId,
            @RequestParam(required = false) StatusFila status,
            @RequestParam(required = false)LocalDate dataDia,
            @RequestParam(required = false) Integer ordemFila,
            @RequestParam(required = false, defaultValue = "10") int limit
            ) {
        var response = buscaPaginadaFilaUseCase.execute(ordemFila,medicoId,status,dataDia,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{filaId}/chamar")
    @PreAuthorize("hasAnyRole('MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<Void> chamarPaciente(
            @PathVariable UUID filaId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        pacienteChamadoFilaUseCase.execute(filaId,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PatchMapping("/{filaId}/status")
    @PreAuthorize("hasAnyRole('MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable UUID filaId,
            @RequestBody @Valid AtualizarStatusFilaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        atualizarStatusFilaUseCase.execute(filaId,request,usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }


}
