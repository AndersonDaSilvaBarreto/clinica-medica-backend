package com.topicos_especiais_1.clinica_medica.consulta.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "reagendamentos_consulta")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReagendamentoConsulta extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @Column(name = "data_hora_inicio_antiga", nullable = false)
    private Instant dataHoraInicioAntiga;

    @Column(name = "data_hora_fim_antiga", nullable = false)
    private Instant dataHoraFimAntiga;

    @Column(name = "data_hora_inicio_nova", nullable = false)
    private Instant dataHoraInicioNova;

    @Column(name = "data_hora_fim_nova", nullable = false)
    private Instant dataHoraFimNova;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reagendado_por", nullable = false)
    private Usuario reagendadoPor;
}
