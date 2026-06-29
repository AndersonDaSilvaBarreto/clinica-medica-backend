package com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.enums.TipoNotificacao;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;

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
@Table(name = "notificacoes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notificacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 60)
    private TipoNotificacao tipo;

    @Column(name = "mensagem", nullable = false, columnDefinition = "text")
    private String mensagem;

    @Column(name = "lida", nullable = false)
    private boolean lida;

    public Notificacao(Usuario usuario, TipoNotificacao tipo, String mensagem) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.lida = false;
    }

    public static Notificacao criar(Usuario usuario, TipoNotificacao tipo, String mensagem) {
        return new Notificacao(usuario, tipo, mensagem);
    }

    public static Notificacao create(Usuario usuario, String tipo, String mensagem) {
        TipoNotificacao tipoNotificacao;
        try {
            tipoNotificacao = TipoNotificacao.valueOf(tipo);
        } catch (IllegalArgumentException | NullPointerException e) {
            tipoNotificacao = TipoNotificacao.GERAL;
        }
        return new Notificacao(usuario, tipoNotificacao, mensagem);
    }

    public void marcarComoLida() {
        this.lida = true;
    }

    public void notificacaoLida() {
        this.marcarComoLida();
    }
}
