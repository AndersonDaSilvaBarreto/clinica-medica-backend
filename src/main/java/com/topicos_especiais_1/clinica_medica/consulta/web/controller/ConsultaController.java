package com.topicos_especiais_1.clinica_medica.consulta.web.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.AgendarConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.AtualizarStatusConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.AtualizarStatusConsultaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.BuscaPaginadaConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.BuscaPaginadaMedicoMeConsultasUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.BuscaPaginadaPacienteMeConsultasUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.BuscaPaginadaReagendamentoConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.CancelarConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.ReagendamentoEmMassaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.ReagendarConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.AgendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.CancelarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ConsultaResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoEmMassaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoEmMassaResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final AgendarConsultaUseCase agendarConsultaUseCase;
    private final AtualizarStatusConsultaUseCase atualizarStatusConsultaUseCase;
    private final BuscaPaginadaConsultaUseCase buscaPaginadaConsultaUseCase;
    private final BuscaPaginadaPacienteMeConsultasUseCase buscaPaginaPacienteMeConsultasUseCase;
    private final BuscaPaginadaMedicoMeConsultasUseCase buscaPaginadaMedicoMeConsultasUseCase;
    private final CancelarConsultaUseCase cancelarConsultaUseCase;
    private final ReagendarConsultaUseCase reagendarConsultaUseCase;
    private final ReagendamentoEmMassaUseCase reagendamentoEmMassaUseCase;
    private final BuscaPaginadaReagendamentoConsultaUseCase buscaPaginadaReagendamentoConsultaUseCase;

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
        var response = buscaPaginadaConsultaUseCase.execute(cursor, pacienteId, medicoId, status, dataInicio, dataFim, limit);
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
        cancelarConsultaUseCase.execute(consultaId, cancelarConsultaRequest.motivo(), usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("/reagendamento-em-massa")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<ReagendamentoEmMassaResponse> reagendamentoEmMassa(
            @RequestBody @Valid ReagendamentoEmMassaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        ReagendamentoEmMassaResponse response = reagendamentoEmMassaUseCase.execute(
                request.medicoId(),
                request.data(),
                request.dataFimEfetiva(),
                request.motivo(),
                usuarioAutenticado.usuario()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{consultaId}/status")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMINISTRADOR')")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable UUID consultaId,
            @RequestBody @Valid AtualizarStatusConsultaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        atualizarStatusConsultaUseCase.execute(consultaId, request.status(), usuarioAutenticado.usuario());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{consultaId}/reagendar")
    public ResponseEntity<Void> reagendar(
            @PathVariable UUID consultaId,
            @RequestBody @Valid ReagendarConsultaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        reagendarConsultaUseCase.execute(consultaId, request.inicio(), request.motivo(), usuarioAutenticado.usuario());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/{consultaId}/historico-reagendamento")
    public ResponseEntity<PaginacaoResponse<ReagendamentoResponse>> historicoReagendamento(
            @RequestParam(required = false) UUID cursor,
            @PathVariable UUID consultaId,
            @RequestParam(required = false) UUID pacienteId,
            @RequestParam(required = false) Instant depoisDe,
            @RequestParam(required = false) Instant antesDe,
            @RequestParam(required = false, defaultValue = "05") int limit
    ) {

        var response = buscaPaginadaReagendamentoConsultaUseCase.execute(
                cursor,
                consultaId,
                pacienteId,
                depoisDe,
                antesDe,
                limit
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
