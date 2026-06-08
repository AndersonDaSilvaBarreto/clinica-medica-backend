package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @GetMapping
    public ResponseEntity<String> pegarUsuario() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Testando");
    }
}
