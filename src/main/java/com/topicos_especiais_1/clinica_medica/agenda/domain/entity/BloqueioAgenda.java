package com.topicos_especiais_1.clinica_medica.agenda.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "bloqueios_agenda")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BloqueioAgenda extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id",nullable = false)
    private Medico medico;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim",nullable = false)
    private LocalDate dataFim;
    @Column(name = "motivo", length = 500)
    private String motivo;

    private BloqueioAgenda(Medico medico, LocalDate dataInicio, LocalDate dataFim, String motivo) {
        this.medico = Objects.requireNonNull(medico);
        validarData(dataInicio,dataFim);
        this.dataInicio = Objects.requireNonNull(dataInicio);
        this.dataFim = Objects.requireNonNull(dataFim);
        if(motivo != null) {
            if(motivo.isBlank() || motivo.length() < 15 || motivo.length() > 500) {
                throw FormatoInvalidoException.from("Bloqueio de Agenda", "motivo deve ter entre 15 e 500 caracteres");

            }
        }
        this.motivo = motivo;

    }
    public static BloqueioAgenda create(
            Medico medico,
            LocalDate dataInicio,
            LocalDate dataFim,
            String motivo
    ){
        return new BloqueioAgenda(medico,dataInicio,dataFim,motivo);
    }
    public void novaDataInicioEFim(
            LocalDate dataInicio,
            LocalDate dataFim
    ) {
        validarData(dataInicio,dataFim);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;

    }
    private static void validarData(
            LocalDate dataInicio,
            LocalDate dataFim
    ) {
        Objects.requireNonNull(dataInicio);
        Objects.requireNonNull(dataFim);

        if (!dataInicio.isBefore(dataFim)) {
            throw FormatoInvalidoException.from(
                    "Bloqueio de Agenda",
                    "Data de inicio precisa ser antes da data de fim");
        }
    }
    public Optional<String> getMotivo() {
        return Optional.ofNullable(motivo);
    }
}
