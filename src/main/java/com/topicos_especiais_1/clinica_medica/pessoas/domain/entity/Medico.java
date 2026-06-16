package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medico extends BaseEntity implements Serializable {

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;
    @Embedded
    private Crm crm;

    @Column(name = "tempo_consulta_minutos", nullable = false)
    private Integer tempoConsultaMinutos;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    public Medico(UUID usuarioId, Crm crm, Integer tempoConsulta, Boolean ativo) {
        this.usuarioId = Objects.requireNonNull(usuarioId);
        this.crm = Objects.requireNonNull(crm);
        this.tempoConsultaMinutos = tempoConsulta != null ? tempoConsulta : 20;
        this.ativo = ativo != null ? ativo : true;
    }
    public static Medico create(
            UUID usuarioId,
            Crm crm,
            Integer tempoConsulta,
            Boolean ativo
    ) {
        return new Medico(usuarioId,crm,tempoConsulta,ativo);
    }
    public void mudarCrm(Crm crm) {
        this.crm = Objects.requireNonNull(crm);
    }
    public void mudarTempoConsulta(Integer tempoConsulta) {
        this.tempoConsultaMinutos = Objects.requireNonNull(tempoConsulta);
    }
    public void ativar() {
        this.ativo = true;
    }
    public void desativar() {
        this.ativo = false;
    }
}
