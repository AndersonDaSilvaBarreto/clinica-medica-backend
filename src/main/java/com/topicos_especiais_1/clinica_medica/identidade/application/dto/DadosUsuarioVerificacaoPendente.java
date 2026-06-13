package com.topicos_especiais_1.clinica_medica.identidade.application.dto;


import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;

public record DadosUsuarioVerificacaoPendente(
        Nome nome,
        Email email,
        Senha senha,
        Genero genero,
        Cpf cpf,
        DataNascimento dataNascimento,
        String codigo
) {
}
