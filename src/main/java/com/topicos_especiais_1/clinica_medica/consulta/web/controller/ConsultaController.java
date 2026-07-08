package com.topicos_especiais_1.clinica_medica.consulta.web.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.topicos_especiais_1.clinica_medica.atendimento.application.usecase.FinalizarAtendimentoUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.*;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.*;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.MensagemResponse;
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
    private final FinalizarAtendimentoUseCase finalizarAtendimentoUseCase; // NOVO

    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(
            @RequestBody @Valid AgendarConsultaRequest agendarConsultaRequest,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        var consulta = agendarConsultaUseCase.execute(agendarConsultaRequest, usuarioAutenticado.usuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaResponse.fromEntity(consulta));
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
        return ResponseEntity.ok(response);
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
        var response = buscaPaginaPacienteMeConsultasUseCase.execute(cursor, usuarioAutenticado.usuario(), medicoId, status, dataInicio, dataFim, limit);
        return ResponseEntity.ok(response);
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
        var response = buscaPaginadaMedicoMeConsultasUseCase.execute(cursor, pacienteId, usuarioAutenticado.usuario(), status, dataInicio, dataFim, limit);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{consultaId}/cancelar")
    public ResponseEntity<MensagemResponse> cancelar(
            @PathVariable UUID consultaId,
            @RequestBody CancelarConsultaRequest cancelarConsultaRequest,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        cancelarConsultaUseCase.execute(consultaId, cancelarConsultaRequest.motivo(), usuarioAutenticado.usuario());
        return ResponseEntity.ok(MensagemResponse.of("Consulta cancelada com sucesso."));
    }

    @PostMapping("/reagendamento-em-massa")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<ReagendamentoEmMassaResponse> reagendamentoEmMassa(
            @RequestBody @Valid ReagendamentoEmMassaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        ReagendamentoEmMassaResponse response = reagendamentoEmMassaUseCase.execute(
                request.medicoId(), request.data(), request.dataFimEfetiva(), request.motivo(), usuarioAutenticado.usuario()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{consultaId}/status")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMINISTRADOR')")
    public ResponseEntity<MensagemResponse> atualizarStatus(
            @PathVariable UUID consultaId,
            @RequestBody @Valid AtualizarStatusConsultaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        atualizarStatusConsultaUseCase.execute(consultaId, request.status(), usuarioAutenticado.usuario());
        return ResponseEntity.ok(MensagemResponse.of("Status da consulta atualizado com sucesso."));
    }

    @PatchMapping("/{consultaId}/reagendar")
    public ResponseEntity<MensagemResponse> reagendar(
            @PathVariable UUID consultaId,
            @RequestBody @Valid ReagendarConsultaRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        reagendarConsultaUseCase.execute(consultaId, request.inicio(), request.motivo(), usuarioAutenticado.usuario());
        return ResponseEntity.ok(MensagemResponse.of("Consulta reagendada com sucesso."));
    }

    /**
     * PATCH /consultas/{consultaId}/finalizar         [NOVO]
     * Médico finaliza o atendimento sem prontuário imediato (ou quando não há próximo na fila).
     *   StatusConsulta: EM_ATENDIMENTO → FINALIZADA
     * Apenas o médico dono da consulta pode executar.
     * Nota: RegistrarProntuarioUseCase também finaliza — este endpoint é para quando
     * o médico encerra o atendimento sem salvar prontuário naquele momento.
     */
    @PatchMapping("/{consultaId}/finalizar")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<MensagemResponse> finalizar(
            @PathVariable UUID consultaId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        finalizarAtendimentoUseCase.execute(consultaId, usuarioAutenticado.usuario());
        return ResponseEntity.ok(MensagemResponse.of("Atendimento finalizado com sucesso."));
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
        var response = buscaPaginadaReagendamentoConsultaUseCase.execute(cursor, consultaId, pacienteId, depoisDe, antesDe, limit);
        return ResponseEntity.ok(response);
    }
}
