package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record CriarBloqueioAgendaRequest(
        @NotNull(message = "MedicoId é obrigatório")
        UUID medicoId,
        @NotNull(message = "Data de Inicio obrigatório")
        @Future
        LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatório")
        @Future
        LocalDate dataFim,
        @Length(min = 15, max = 500, message = "Motivo deve ter entre 15 a 500 caracteres")
        String motivo
) {
}
