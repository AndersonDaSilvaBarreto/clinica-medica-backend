package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CriarSalaAtendimentoRequest(
        @NotBlank(message = "Nome não pode ser vazio")
        @Length(min=2,max=150,message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @Length(min = 15, max = 500, message = "Descrição deve ter de 15 até 500 caracteres")
        String descricao,
        Boolean ativa
) {
}
