package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegistrarProntuarioRequest(
        @NotBlank(message = "O histórico clínico é obrigatório.")
        @Length(min = 10)
        String historico,

        @NotBlank(message = "A receita médica é obrigatória.")
        @Length(min = 10)
        String receita,
        String examesSolicitados
) {
}
