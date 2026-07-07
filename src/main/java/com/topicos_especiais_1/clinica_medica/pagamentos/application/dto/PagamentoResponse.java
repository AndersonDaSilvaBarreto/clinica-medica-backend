package com.topicos_especiais_1.clinica_medica.pagamentos.application.dto;

public record PagamentoResponse(

        java.util.UUID id,

        String paymentId,

        String status,

        String statusDetail,

        String qrCode,

        String qrCodeBase64

) {
}