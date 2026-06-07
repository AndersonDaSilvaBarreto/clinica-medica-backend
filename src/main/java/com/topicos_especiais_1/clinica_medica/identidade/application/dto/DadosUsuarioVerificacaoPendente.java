package com.topicos_especiais_1.clinica_medica.identidade.application.dto;


public record DadosUsuarioVerificacaoPendente(
        String nome,
        String email,
        String senha,
        String codigo
) {
}
