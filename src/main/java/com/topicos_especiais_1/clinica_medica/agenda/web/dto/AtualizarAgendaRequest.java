package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record AtualizarAgendaRequest(
        @NotEmpty(message = "A gente deve possuir pelo menos um dia")
        List<@Valid DiaAgendaRequest> dias

) {

    public record DiaAgendaRequest(
            @NotNull(message = "Dia da semana é obrigatório") DiaSemana diaSemana,
                                    @NotEmpty(message = "O dia deve possuir pelo menos um período") List<@Valid PeriodoRequest> periodos ) { }

    public record PeriodoRequest(
            @NotNull(message = "Hora inicial é obrigatória") LocalTime horaInicio,
            @NotNull(message = "Hora final é obrigatória") LocalTime horaFim
    ){}
}
