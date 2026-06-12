package com.topicos_especiais_1.clinica_medica.identidade.application.dto;


import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.CPF;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;

public record DadosUsuarioVerificacaoPendente(
        Nome nome,
        Email email,
        Senha senha,
        Genero genero,
        CPF cpf,
        DataNascimento dataNascimento,
        String codigo
) {
}
