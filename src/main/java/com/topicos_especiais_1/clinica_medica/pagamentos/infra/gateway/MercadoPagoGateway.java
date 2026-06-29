package com.topicos_especiais_1.clinica_medica.pagamentos.infra.gateway;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;

public interface MercadoPagoGateway {

    PagamentoResponse pagarComCartao(
            CriarPagamentoRequest request
    );

    PagamentoResponse gerarPix(
            CriarPagamentoRequest request
    );

    PagamentoResponse consultarPagamento(
            String paymentId
    );
}