package com.topicos_especiais_1.clinica_medica.shared.web;

import com.topicos_especiais_1.clinica_medica.shared.api.ErroResponse;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoEmailInvalidoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(
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
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroResponse.ofValidacao(request.getRequestURI(),erros));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenerico(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro inesperado: ", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErroResponse.of(
                        "Erro interno do servidor",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        request.getRequestURI())
                );
    }

    @ExceptionHandler(FormatoEmailInvalidoException.class)
    public ResponseEntity<ErroResponse> handleEmailInvalido(
            FormatoEmailInvalidoException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI()));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> handleEntidadeNaoEncontrada(
            EntidadeNaoEncontradaException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,request.getRequestURI()));
    }
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErroResponse> handleNaoAutenticado(
            InsufficientAuthenticationException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErroResponse.of("Não autenticado", HttpStatus.UNAUTHORIZED, request.getRequestURI()));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErroResponse> handleAcessoNaoAutorizado(
            AuthorizationDeniedException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErroResponse.of("Não autorizado", HttpStatus.FORBIDDEN, request.getRequestURI()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleSemPermissao(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErroResponse.of("Acesso negado", HttpStatus.FORBIDDEN, request.getRequestURI()));
    }
}

