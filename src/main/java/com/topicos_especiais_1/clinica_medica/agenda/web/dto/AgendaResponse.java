package com.topicos_especiais_1.clinica_medica.agenda.web.dto;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public record AgendaResponse(
        List<DiaAgendaResponse> dias
) {

    public static AgendaResponse from(List<HorarioAtendimento> horarios) {
        List<DiaAgendaResponse> dias = horarios.stream()
                .collect(Collectors.groupingBy(
                        HorarioAtendimento::getDiaSemana,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().ordinal()))
                .map(entry ->
                        new DiaAgendaResponse(
                                entry.getKey(),
                                entry.getValue()
                                        .stream()
                                        .map(horario ->
                                                new PeriodoResponse(
                                                        horario.getHoraInicio(),
                                                        horario.getHoraFim()))
                                        .toList())).toList();
        return new AgendaResponse(dias);
    }

    public record DiaAgendaResponse(DiaSemana diaSemana, List<PeriodoResponse> periodos) {
    }

    public record PeriodoResponse(LocalTime horaInicio, LocalTime horaFim) {
    }
}
