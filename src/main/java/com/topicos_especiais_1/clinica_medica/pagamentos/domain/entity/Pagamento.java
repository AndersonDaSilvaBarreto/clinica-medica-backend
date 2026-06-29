package com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.FormaPagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.StatusPagamento;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamentos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagamento extends BaseEntity {

    @Column(name = "consulta_id", nullable = false)
    private UUID consultaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "payment_id_mp")
    private String paymentIdMp;

    @Column(name = "status_detail")
    private String statusDetail;

    @Column(name = "qr_code", columnDefinition = "TEXT")
    private String qrCode;

    @Column(name = "qr_code_base64", columnDefinition = "TEXT")
    private String qrCodeBase64;

    public Pagamento(
            UUID consultaId,
            FormaPagamento formaPagamento,
            StatusPagamento status,
            BigDecimal valor,
            String paymentIdMp,
            String statusDetail,
            String qrCode,
            String qrCodeBase64
    ) {
        this.consultaId = consultaId;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.valor = valor;
        this.paymentIdMp = paymentIdMp;
        this.statusDetail = statusDetail;
        this.qrCode = qrCode;
        this.qrCodeBase64 = qrCodeBase64;
    }

    public void aprovar(String statusDetail) {
        this.status = StatusPagamento.APROVADO;
        this.statusDetail = statusDetail;
    }

    public void recusar(String statusDetail) {
        this.status = StatusPagamento.RECUSADO;
        this.statusDetail = statusDetail;
    }

    public void cancelar() {
        this.status = StatusPagamento.CANCELADO;
    }

    public boolean estaFinalizado() {
        return this.status == StatusPagamento.APROVADO
                || this.status == StatusPagamento.RECUSADO
                || this.status == StatusPagamento.CANCELADO;
    }
}
