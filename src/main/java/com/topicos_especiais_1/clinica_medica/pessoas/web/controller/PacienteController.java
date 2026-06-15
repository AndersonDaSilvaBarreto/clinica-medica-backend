package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.AtualizarPacienteUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscarPacientesPaginadoUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarDadosPacienteRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscarPacienteAutenticadoUseCase;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final BuscarPacienteAutenticadoUseCase buscarPacientePorIdUseCase;
    private final AtualizarPacienteUseCase atualizarPacienteUseCase;
    private final BuscarPacientesPaginadoUseCase buscarPacientesPaginadoUseCase;
    @GetMapping("/me")
    public ResponseEntity<PacienteResponse> pegarUsuario(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buscarPacientePorIdUseCase.execute(usuarioAutenticado));
    }
    @PatchMapping("/me")
    public ResponseEntity<Void> atualizarUsuarioAutenticado(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestBody @Valid AtualizarDadosPacienteRequest request
            ) {
            atualizarPacienteUseCase.execute(usuarioAutenticado.getId(), request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'RECEPCIONISTA')")
    public ResponseEntity<PaginacaoResponse<PacienteResponse>> buscarPacientesPaginado(
            @RequestParam(required = false)UUID cursor,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String busca
            ) {
        var response = buscarPacientesPaginadoUseCase.execute(
                cursor,
                limit,
                busca
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
