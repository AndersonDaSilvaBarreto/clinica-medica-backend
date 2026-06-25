package com.topicos_especiais_1.clinica_medica.pagamentos.application.dto;

public record PagamentoResponse(

        String paymentId,

        String status,

        String statusDetail,

        String qrCode,

        String qrCodeBase64

) {
}