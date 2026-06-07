package com.topicos_especiais_1.clinica_medica.identidade.domain.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CodeGenerator {
    public static String gerarCodigo() {
        return String.format("%06d", new SecureRandom().nextInt(999999));
    }
}
