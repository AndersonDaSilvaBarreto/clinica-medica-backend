package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topicos_especiais_1.clinica_medica.agenda.application.usecase.BuscarHorariosMedicoUseCase;
import com.topicos_especiais_1.clinica_medica.agenda.domain.enums.StatusHorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.HorarioMedicoResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/medicos/{medicoId}/horarios")
@RequiredArgsConstructor
public class HorarioMedicoController {

    private final BuscarHorariosMedicoUseCase buscarHorariosUseCase;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HorarioMedicoResponse>> listar(
            @PathVariable UUID medicoId,
            @RequestParam(required = false) StatusHorarioMedico status
    ) {
        List<HorarioMedicoResponse> response = buscarHorariosUseCase
                .execute(medicoId, status)
                .stream()
                .map(HorarioMedicoResponse::de)
                .toList();

        return ResponseEntity.ok(response);
    }
}
