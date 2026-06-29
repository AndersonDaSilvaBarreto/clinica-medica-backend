package com.topicos_especiais_1.clinica_medica.atendimento.domain.entity;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "fila_atendimento")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilaAtendimento extends BaseEntity implements Serializable {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id",nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id",nullable = false)
    private SalaAtendimento sala;

    @Column(name = "ordem_fila", nullable = false)
    private Integer ordemFila;

    @Column(name = "data_fila",nullable = false)
    private LocalDate dataFila;

    @Column(name = "horario_chamada")
    private Instant horarioChamada;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private StatusFila status;

    private FilaAtendimento(Consulta consulta, Medico medico,Paciente paciente, SalaAtendimento sala, Integer ordemFila, LocalDate dataFila) {
        this.consulta = Objects.requireNonNull(consulta);
        this.medico = Objects.requireNonNull(medico);
        this.paciente = Objects.requireNonNull(paciente);
        this.sala = Objects.requireNonNull(sala);
        this.ordemFila = Objects.requireNonNull(ordemFila);
        this.dataFila = Objects.requireNonNull(dataFila);
        this.status = StatusFila.AGUARDANDO;
    }
    public static FilaAtendimento create(Consulta consulta, Medico medico,Paciente paciente, SalaAtendimento sala, Integer ordemFila, LocalDate dataFila) {
        return new FilaAtendimento(consulta,medico,paciente,sala,ordemFila,dataFila);
    }

    public void chamarParaAtendimento() {
        this.status = StatusFila.CHAMADO;
        this.horarioChamada = Instant.now();
    }
    public void finalizarAtendimento() {
        this.status = StatusFila.ATENDIDO;
    }

    public void mudarStatus(StatusFila status) {
        this.status = Objects.requireNonNull(status);
    }
}
