package com.topicos_especiais_1.clinica_medica.identidade.application.dto;


import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;

public record DadosUsuarioVerificacaoPendente(
        Nome nome,
        Email email,
        Senha senha,
        Genero genero,
        Cpf cpf,
        DataNascimento dataNascimento,
        Telefone telefone,
        String endereco,
        String codigo

) {
}
