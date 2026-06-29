package com.topicos_especiais_1.clinica_medica.consulta.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultas", schema = "public")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consulta extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "data_hora_inicio", nullable = false)
    private Instant dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private Instant dataHoraFim;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private StatusConsulta statusConsulta;

    @Column(name = "observacao", columnDefinition = "text")
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por", nullable = false)
    private Usuario criadoPor;

    public Consulta(Paciente paciente, Medico medico, Instant dataHoraInicio, Instant dataHoraFim, StatusConsulta statusConsulta, String observacao, Usuario criadoPor) {
        this.paciente = Objects.requireNonNull(paciente);
        this.medico = Objects.requireNonNull(medico);
        this.dataHoraInicio = Objects.requireNonNull(dataHoraInicio);
        this.dataHoraFim = Objects.requireNonNull(dataHoraFim);
        validarDataHora(dataHoraInicio, dataHoraFim);
        this.statusConsulta = Objects.requireNonNull(statusConsulta);
        String obsNormalizada = observacao != null ? observacao.trim() : null;
        validarObservacao(obsNormalizada);
        this.observacao = obsNormalizada;
        this.criadoPor = Objects.requireNonNull(criadoPor);
    }

    public static Consulta create(Paciente paciente, Medico medico, Instant dataHoraInicio, Instant dataHoraFim, String observacao, Usuario criadoPor) {
        return new Consulta(paciente, medico, dataHoraInicio, dataHoraFim, StatusConsulta.AGUARDANDO_PAGAMENTO, observacao, criadoPor); //
    }

    private static void validarDataHora(Instant dataHoraInicio, Instant dataHoraFim) {
        if (!dataHoraInicio.isBefore(dataHoraFim)) {
            throw FormatoInvalidoException.from("Consulta", "Data e hora inicial precisa ser antes do fim.");
        }
    }

    private static void validarObservacao(String observacao) {
        if (observacao != null && (observacao.length() < 15 || observacao.length() > 500)) {
            throw FormatoInvalidoException.from("Consulta", "Observação deve ter de 15 até 500 caracteres.");
        }
    }
    public void cancelar(String motivo, Usuario usuarioOperador) {
        if (this.statusConsulta == StatusConsulta.CANCELADA) {
            throw FormatoInvalidoException.from("Consulta", "Esta consulta já está cancelada.");
        }

        this.statusConsulta = StatusConsulta.CANCELADA;

        String blocoMotivo = "\n[CANCELAMENTO - " + Instant.now() + " por " + usuarioOperador.getNome() + "]: " + motivo;
        this.observacao = this.observacao == null ? blocoMotivo.trim() : this.observacao + blocoMotivo;
    }

    public void reagendar(Instant novoInicio, Instant novoFim, String motivo, Usuario usuarioOperador) {
        if (this.statusConsulta == StatusConsulta.CANCELADA) {
            throw FormatoInvalidoException.from("Consulta", "Não é possível reagendar uma consulta cancelada.");
        }

        validarDataHora(novoInicio, novoFim);

        this.dataHoraInicio = novoInicio;
        this.dataHoraFim = novoFim;
        this.statusConsulta = StatusConsulta.AGUARDANDO_PAGAMENTO;

        String blocoMotivo = "\n[REAGENDAMENTO - " + Instant.now() + " por " + usuarioOperador.getNome() + "]: " + motivo;
        this.observacao = this.observacao == null ? blocoMotivo.trim() : this.observacao + blocoMotivo;
    }

    public void marcarFaltou() {
        if (this.statusConsulta != StatusConsulta.AGENDADA && this.statusConsulta != StatusConsulta.REAGENDADA) {
            throw FormatoInvalidoException.from("Consulta",
                    "Apenas consultas com status AGENDADA ou REAGENDADA podem ser marcadas como FALTOU.");
        }
        this.statusConsulta = StatusConsulta.FALTOU;
    }

    public void mudarStatus(StatusConsulta statusConsulta) {
        this.statusConsulta = Objects.requireNonNull(statusConsulta);
    }
}
