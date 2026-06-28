package com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notificacao extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @Column(name = "tipo",nullable = false)
    private String tipo;

    @Column(name = "mensagem",nullable = false)
    private String mensagem;

    @Column(name = "lida",nullable = false)
    private Boolean lida;

    public Notificacao(Usuario usuario, String tipo, String mensagem, Boolean lida) {
        this.usuario = Objects.requireNonNull(usuario);
        this.tipo = Objects.requireNonNull(tipo);
        this.mensagem = Objects.requireNonNull(mensagem);
        this.lida = Objects.requireNonNull(lida);
    }

    public static Notificacao create(Usuario usuario, String tipo, String mensagem) {
        return new Notificacao(usuario,tipo,mensagem,false);
    }

    public void notificacaoLida() {
        if(this.lida != true) {
            this.lida = true;
        }
    }
}
