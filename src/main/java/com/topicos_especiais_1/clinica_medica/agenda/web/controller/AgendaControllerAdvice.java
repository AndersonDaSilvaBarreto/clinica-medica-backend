package com.topicos_especiais_1.clinica_medica.agenda.web.controller;

import com.topicos_especiais_1.clinica_medica.agenda.domain.exception.HorarioAtendimentoInvalidoException;
import com.topicos_especiais_1.clinica_medica.shared.api.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AgendaControllerAdvice {

    @ExceptionHandler(HorarioAtendimentoInvalidoException.class)
    public ResponseEntity<ErroResponse> horarioAtendimentoInvalido(
            HorarioAtendimentoInvalidoException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI()));
    }
}
