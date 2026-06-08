package com.topicos_especiais_1.clinica_medica.shared.web;

import com.topicos_especiais_1.clinica_medica.shared.api.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handlleValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ErroResponse.ErroDetalhe> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErroResponse.ErroDetalhe(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )
                ).toList();
        return ResponseEntity
                .badRequest()
                .body(ErroResponse.ofValidacao(request.getRequestURI(),erros));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenerico(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro inesperado: ", ex);

        return ResponseEntity
                .internalServerError()
                .body(ErroResponse.of(
                        "Erro interno do servidor",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        request.getRequestURI())
                );
    }
}

