package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import org.hibernate.validator.constraints.Length;

public record CancelarConsultaRequest(
        @Length(min = 15,max = 500)
        String motivo
) {
}
