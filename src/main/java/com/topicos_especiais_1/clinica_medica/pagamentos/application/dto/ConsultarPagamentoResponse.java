package com.topicos_especiais_1.clinica_medica.pagamentos.application.dto;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.FormaPagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.StatusPagamento;

import java.math.BigDecimal;
import java.util.UUID;

public record ConsultarPagamentoResponse(

        UUID id,

        UUID consultaId,

        FormaPagamento formaPagamento,

        StatusPagamento status,

        BigDecimal valor,

        String paymentIdMp,

        String statusDetail,

        String qrCode,

        String qrCodeBase64

) {
}