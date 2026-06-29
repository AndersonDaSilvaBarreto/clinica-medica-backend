package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record ReagendamentoEmMassaRequest(

        @NotNull(message = "O id do médico é obrigatório.")
        UUID medicoId,

        @NotNull(message = "A data de início do período a ser reagendado é obrigatória.")
        LocalDate data,

        // Opcional: se nulo ou igual a `data`, trata como dia único
        LocalDate dataFim,

        @NotBlank(message = "O motivo do reagendamento é obrigatório.")
        @Length(min = 15, max = 500, message = "O motivo deve ter entre 15 e 500 caracteres.")
        String motivo
) {
    /** Retorna a data de fim efetiva: dataFim se preenchida e posterior a data, caso contrário o próprio data. */
    public LocalDate dataFimEfetiva() {
        return (dataFim != null && dataFim.isAfter(data)) ? dataFim : data;
    }
}
