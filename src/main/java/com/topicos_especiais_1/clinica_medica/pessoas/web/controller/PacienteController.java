package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.AtualizarPacienteUseCase;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarDadosPacienteRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscarPacienteAutenticadoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final BuscarPacienteAutenticadoUseCase buscarPacientePorIdUseCase;
    private final AtualizarPacienteUseCase atualizarPacienteUseCase;
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
}
