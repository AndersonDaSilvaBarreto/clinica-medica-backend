package com.topicos_especiais_1.clinica_medica.consulta.web.controller;

import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.RegistrarProntuarioUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ProntuarioResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.RegistrarProntuarioRequest;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consultas/{consultaId}/prontuario")
@RequiredArgsConstructor
public class ProntuarioController {
    private final RegistrarProntuarioUseCase registrarProntuarioUseCase;

    @PostMapping
    public ResponseEntity<ProntuarioResponse>  salvar(
            @PathVariable UUID consultaId,
            @RequestBody @Valid RegistrarProntuarioRequest request,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
            ) {
            var response = registrarProntuarioUseCase.execute(
                    consultaId,request,usuarioAutenticado.usuario()
            );
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
    }
}
