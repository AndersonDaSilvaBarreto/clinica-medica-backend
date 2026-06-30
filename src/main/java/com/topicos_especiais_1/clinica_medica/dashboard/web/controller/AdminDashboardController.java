package com.topicos_especiais_1.clinica_medica.dashboard.web.controller;

import com.topicos_especiais_1.clinica_medica.dashboard.application.usecase.CardsUseCase;
import com.topicos_especiais_1.clinica_medica.dashboard.application.usecase.ConsultasPeriodoUseCase;
import com.topicos_especiais_1.clinica_medica.dashboard.application.usecase.ProximasConsultasUseCase;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.CardsResponse;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.ConsultasPeriodoResponse;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.ProximasConsultasResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final CardsUseCase cardsUseCase;
    private final ConsultasPeriodoUseCase consultasPeriodoUseCase;
    private final ProximasConsultasUseCase proximasConsultasUseCase;
    @GetMapping("/cards")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<CardsResponse> cards(
            @RequestParam(required = false)LocalDate dataInicio,
            @RequestParam(required = false)LocalDate dataFim
            ) {
        CardsResponse response = cardsUseCase.execute(dataInicio,dataFim);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/consultas-periodo")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ConsultasPeriodoResponse> consultasPeriodo(
            @RequestParam(required = false)LocalDate dataInicio,
            @RequestParam(required = false)LocalDate dataFim
    ) {
        ConsultasPeriodoResponse response = consultasPeriodoUseCase.execute(dataInicio,dataFim);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/proximas-consultas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PaginacaoResponse<ProximasConsultasResponse>> proximasConsultas(
            @RequestParam(required = false)LocalDate dataInicio,
            @RequestParam(required = false,defaultValue = "10") int limit,
            @RequestParam(required = false)UUID cursor
            ) {
        PaginacaoResponse<ProximasConsultasResponse> response = proximasConsultasUseCase.execute(
                cursor,dataInicio,limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
