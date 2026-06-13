package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.BuscarPacientePorUsuarioIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final BuscarPacientePorUsuarioIdUseCase buscarPacientePorIdUseCase;
    @GetMapping("/me")
    public ResponseEntity<PacienteResponse> pegarUsuario(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buscarPacientePorIdUseCase.execute(usuarioAutenticado));
    }
}
