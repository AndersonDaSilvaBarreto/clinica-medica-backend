package com.topicos_especiais_1.clinica_medica.agenda.domain.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.agenda.domain.exception.HorarioAtendimentoInvalidoException;
import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "horarios_atendimento", uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "medico_id",
            "dia_semana",
            "hora_inicio",
            "hora_fim"
        }
))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorarioAtendimento implements Serializable {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "dia_semana", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    private HorarioAtendimento(
            Medico medico,
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFim) {
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.medico = Objects.requireNonNull(medico);
        this.diaSemana = Objects.requireNonNull(diaSemana);
        this.horaInicio = Objects.requireNonNull(horaInicio);
        this.horaFim = Objects.requireNonNull(horaFim);
        validarHorario(horaInicio, horaFim);
    }

    public static HorarioAtendimento create(
            Medico medico,
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFim
    ) {

        return new HorarioAtendimento(
                medico,
                diaSemana,
                horaInicio,
                horaFim
        );
    }

    public void novaHoraDeInicioEFim(
            LocalTime horaInicio,
            LocalTime horaFim
    ) {
        validarHorario(horaInicio, horaFim);
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;

    }

    private static void validarHorario(
            LocalTime horaInicio,
            LocalTime horaFim
    ) {
        Objects.requireNonNull(horaInicio);
        Objects.requireNonNull(horaFim);

        if (!horaInicio.isBefore(horaFim)) {
            throw HorarioAtendimentoInvalidoException.horarioInicioDepoisDeFim();
        }
    }

    public DiaSemana getDiaSemana() {
        return this.diaSemana;
    }

}
