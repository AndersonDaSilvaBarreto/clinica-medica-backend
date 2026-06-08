package com.topicos_especiais_1.clinica_medica.identidade.web;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioExistenteException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioNaoEncontradoException;
import com.topicos_especiais_1.clinica_medica.shared.api.ErroResponse;
import com.topicos_especiais_1.clinica_medica.shared.exception.FormatoEmailInvalidoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class IdentidadeControllerAdvice {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleNaoEncontrado(
            UsuarioNaoEncontradoException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI()));
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ErroResponse> handleExistente(
            UsuarioExistenteException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.CONFLICT, request.getRequestURI()));
    }

    @ExceptionHandler(FormatoEmailInvalidoException.class)
    public ResponseEntity<ErroResponse> handleEmailInvalido(
            FormatoEmailInvalidoException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .badRequest()
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI()));
    }
}
