package com.topicos_especiais_1.clinica_medica.consulta.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Prontuario extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id",nullable = false,unique = true)
    private Consulta consulta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    @Column(name = "historico",nullable = false,columnDefinition = "TEXT")
    private String historico;
    @Column(name = "receita",nullable = false,columnDefinition = "TEXT")
    private String receita;
    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados;

}
