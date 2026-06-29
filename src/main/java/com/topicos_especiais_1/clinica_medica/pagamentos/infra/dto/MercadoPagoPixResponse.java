package com.topicos_especiais_1.clinica_medica.pagamentos.infra.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MercadoPagoPixResponse {

    private String paymentId;

    private String status;

    private String statusDetail;

    private String qrCode;

    private String qrCodeBase64;
}