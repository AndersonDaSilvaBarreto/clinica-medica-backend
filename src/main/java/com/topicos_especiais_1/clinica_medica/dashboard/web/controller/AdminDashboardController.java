package com.topicos_especiais_1.clinica_medica.dashboard.web.controller;

import com.topicos_especiais_1.clinica_medica.dashboard.application.usecase.CardsUseCase;
import com.topicos_especiais_1.clinica_medica.dashboard.web.dto.CardsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {
    private final CardsUseCase cardsUseCase;
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
}
