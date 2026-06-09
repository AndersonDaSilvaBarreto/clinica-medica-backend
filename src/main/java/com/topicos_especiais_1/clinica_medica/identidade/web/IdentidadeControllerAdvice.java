package com.topicos_especiais_1.clinica_medica.identidade.web;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.*;
import com.topicos_especiais_1.clinica_medica.shared.api.ErroResponse;
import com.topicos_especiais_1.clinica_medica.shared.exception.FormatoNomeInvalidoException;
import com.topicos_especiais_1.clinica_medica.shared.exception.FormatoTelefoneInvalidoException;
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

    @ExceptionHandler(FormatoNomeInvalidoException.class)
    public ResponseEntity<ErroResponse> handleNome(
            FormatoNomeInvalidoException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .badRequest()
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI()));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErroResponse> handleToken(
            TokenInvalidoException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI()));
    }

    @ExceptionHandler(FormatoSenhaInvalidoException.class)
    public ResponseEntity<ErroResponse> handleSenha(
            FormatoSenhaInvalidoException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,request.getRequestURI()));
    }

    @ExceptionHandler(FormatoTelefoneInvalidoException.class)
    public ResponseEntity<ErroResponse> handleTelefone(
            FormatoTelefoneInvalidoException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,request.getRequestURI()));
    }
    @ExceptionHandler(CodigoExpiradoException.class)
    public ResponseEntity<ErroResponse> handleCodigoExpirado(
            CodigoExpiradoException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(ErroResponse.of(
                        ex.getMessage(),
                        HttpStatus.GONE,
                        request.getRequestURI())
                );

    }

    @ExceptionHandler(VerificacaoInvalidaException.class)
    public ResponseEntity<ErroResponse> handleVerificacao(
        VerificacaoInvalidaException ex,
        HttpServletRequest request
    ) {
        return  ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ErroResponse.of(
                        ex.getMessage(),
                        HttpStatus.UNPROCESSABLE_CONTENT,
                        request.getRequestURI())
                );
    }

}
