package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.CriarBloqueioAgendaUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.CriarBloqueioAgendaRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/bloqueios")
@RequiredArgsConstructor
public class AdminBloqueioAgendaController {
    private final CriarBloqueioAgendaUseCase criarBloqueioAgendaUseCase;

    @PostMapping
    public ResponseEntity<Void> criar(
            @RequestBody @Valid CriarBloqueioAgendaRequest request
            ) {
        criarBloqueioAgendaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

}
