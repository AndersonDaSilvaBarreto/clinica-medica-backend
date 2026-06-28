package com.topicos_especiais_1.clinica_medica.agenda.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.agenda.domain.enums.StatusHorarioMedico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa um slot de atendimento gerado automaticamente para um médico.
 * Ex: se o médico atende seg-sex das 07:00 às 12:00 com 20 min de consulta,
 * serão gerados slots 07:00, 07:20, 07:40 ... 11:40 para cada dia do mês.
 */
@Entity
@Table(
    name = "horarios_medico",
    uniqueConstraints = @UniqueConstraint(columnNames = {"medico_id", "data_hora"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HorarioMedico implements Serializable {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    /** Data e hora exata do início do slot (UTC armazenado, exibido em SP). */
    @Column(name = "data_hora", nullable = false)
    private Instant dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusHorarioMedico status;

    @Column(name = "data_criacao", updatable = false, nullable = false)
    private Instant dataCriacao;

    private HorarioMedico(Medico medico, Instant dataHora) {
        this.id          = UuidCreator.getTimeOrderedEpoch();
        this.medico      = Objects.requireNonNull(medico, "medico é obrigatório");
        this.dataHora    = Objects.requireNonNull(dataHora, "dataHora é obrigatório");
        this.status      = StatusHorarioMedico.DISPONIVEL;
        this.dataCriacao = Instant.now();
    }

    public static HorarioMedico criar(Medico medico, Instant dataHora) {
        return new HorarioMedico(medico, dataHora);
    }

    public void marcarOcupado() {
        this.status = StatusHorarioMedico.OCUPADO;
    }

    public void marcarDisponivel() {
        this.status = StatusHorarioMedico.DISPONIVEL;
    }

    public boolean isDisponivel() {
        return StatusHorarioMedico.DISPONIVEL == this.status;
    }
}
