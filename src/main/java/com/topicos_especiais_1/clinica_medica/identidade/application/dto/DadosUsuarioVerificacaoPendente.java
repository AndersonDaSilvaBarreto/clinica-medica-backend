package com.topicos_especiais_1.clinica_medica.identidade.application.dto;


import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.CPF;

public record DadosUsuarioVerificacaoPendente(
        String nome,
        String email,
        String senha,
        CPF cpf,
        String codigo
) {
}
