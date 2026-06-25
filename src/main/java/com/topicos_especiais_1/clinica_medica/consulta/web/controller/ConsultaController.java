package com.topicos_especiais_1.clinica_medica.consulta.web.controller;

import com.topicos_especiais_1.clinica_medica.consulta.application.usecase.AgendarConsultaUseCase;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.AgendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final AgendarConsultaUseCase agendarConsultaUseCase;

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
}
