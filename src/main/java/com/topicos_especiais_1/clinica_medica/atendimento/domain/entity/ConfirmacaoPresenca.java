package com.topicos_especiais_1.clinica_medica.atendimento.domain.entity;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "confirmacoes_presenca")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmacaoPresenca extends BaseEntity implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id",nullable = false)
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recepcionista_id", nullable = false)
    private Recepcionista recepcionista;

    @Column(name = "horario_confirmacao",nullable = false)
    private Instant horarioConfirmacao;

    public ConfirmacaoPresenca(Consulta consulta, Recepcionista recepcionista) {
        this.consulta = Objects.requireNonNull(consulta);
        this.recepcionista = Objects.requireNonNull(recepcionista);
        this.horarioConfirmacao = Instant.now();
    }
    public static ConfirmacaoPresenca create(Consulta consulta, Recepcionista recepcionista) {
        return new ConfirmacaoPresenca(consulta,recepcionista);
    }
}
