package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medico extends BaseEntity implements Serializable {

    @Embedded
    private Crm crm;

    @Column(name = "tempo_consulta_minutos", nullable = false)
    private Integer tempoConsultaMinutos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "medico_especialidades",
            joinColumns = @JoinColumn(name = "medico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private final Set<Especialidade> especialidades = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "medico",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<HorarioAtendimento> horariosAtendimento = new LinkedHashSet<>();

    public Medico(Usuario usuario, Crm crm, Integer tempoConsulta) {
        this.usuario = Objects.requireNonNull(usuario);
        this.crm = Objects.requireNonNull(crm);
        this.tempoConsultaMinutos = tempoConsulta != null ? tempoConsulta : 20;
    }

    public static Medico create(
            Usuario usuario,
            Crm crm,
            Integer tempoConsulta
    ) {
        return new Medico(usuario, crm, tempoConsulta);
    }

    public void adicionarEspecialidade(Especialidade especialidade) {
        especialidades.add(Objects.requireNonNull(especialidade));
    }

    public void removerEspecialidade(Especialidade especialidade) {
        especialidades.remove(Objects.requireNonNull(especialidade));
    }

    public void mudarCrm(Crm crm) {
        this.crm = Objects.requireNonNull(crm);
    }

    public void mudarTempoConsulta(Integer tempoConsulta) {
        this.tempoConsultaMinutos = Objects.requireNonNull(tempoConsulta);
    }

    public void ativar() {
        this.usuario.mudarAtivo(true);
    }

    public void desativar() {
        this.usuario.mudarAtivo(false);
    }

    public void adicionarHorarioAtendimento(HorarioAtendimento horarioAtendimento) {
        horariosAtendimento.add(Objects.requireNonNull(horarioAtendimento));
    }

    public void limparHorariosAtendimento() {
        horariosAtendimento.clear();
    }

    
}
