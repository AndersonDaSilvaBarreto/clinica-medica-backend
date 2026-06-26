package com.topicos_especiais_1.clinica_medica.consulta.web.controller;

import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.*;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.AgendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.CancelarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ConsultaResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final AgendarConsultaUseCase agendarConsultaUseCase;
    private final BuscaPaginadaConsultaUseCase buscaPaginadaConsultaUseCase;
    private final BuscaPaginadaPacienteMeConsultasUseCase buscaPaginaPacienteMeConsultasUseCase;
    private final BuscaPaginadaMedicoMeConsultasUseCase buscaPaginadaMedicoMeConsultasUseCase;
    private final CancelarConsultaUseCase cancelarConsultaUseCase;
    private final ReagendarConsultaUseCase reagendarConsultaUseCase;
    @PostMapping
    public ResponseEntity<Void> agendar(
            @RequestBody @Valid AgendarConsultaRequest agendarConsultaRequest,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        agendarConsultaUseCase.execute(agendarConsultaRequest, usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECEPCIONISTA')")
    public ResponseEntity<PaginacaoResponse<ConsultaResponse>> buscaPaginada(
            @RequestParam(required = false) UUID cursor,
            @RequestParam(required = false) UUID pacienteId,
            @RequestParam(required = false) UUID medicoId,
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(required = false) Instant dataInicio,
            @RequestParam(required = false) Instant dataFim,
            @RequestParam(required = false, defaultValue = "10") int limit
            ) {
        var response = buscaPaginadaConsultaUseCase.execute(cursor,pacienteId,medicoId,status,dataInicio,dataFim,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/pacientes/me")
    public ResponseEntity<PaginacaoResponse<ConsultaResponse>> buscaPaginadaPacienteMe(
            @RequestParam(required = false) UUID cursor,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestParam(required = false) UUID medicoId,
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(required = false) Instant dataInicio,
            @RequestParam(required = false) Instant dataFim,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        var response = buscaPaginaPacienteMeConsultasUseCase.execute(
                cursor,
                usuarioAutenticado.usuario(),
                medicoId,
                status,
                dataInicio,
                dataFim,
                limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/medicos/me")
    public ResponseEntity<PaginacaoResponse<ConsultaResponse>> buscaPaginadaMedicoMe(
            @RequestParam(required = false) UUID cursor,
            @RequestParam(required = false) UUID pacienteId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(required = false) Instant dataInicio,
            @RequestParam(required = false) Instant dataFim,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {

        var response = buscaPaginadaMedicoMeConsultasUseCase.execute(
                cursor,
                pacienteId,
                usuarioAutenticado.usuario(),
                status,
                dataInicio,
                dataFim,
                limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{consultaId}/cancelar")
    public ResponseEntity<Void> cancelar(
        @PathVariable UUID consultaId,
        @RequestBody CancelarConsultaRequest cancelarConsultaRequest,
        @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        cancelarConsultaUseCase.execute(consultaId,cancelarConsultaRequest.motivo(),usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PatchMapping("/{consultaId}/reagendar")
    public ResponseEntity<Void> reagendar(
            @PathVariable UUID consultaId,
            @RequestBody @Valid ReagendarConsultaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
            reagendarConsultaUseCase.execute(consultaId, request.inicio(),request.motivo(),usuarioAutenticado.usuario());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
    }
}
