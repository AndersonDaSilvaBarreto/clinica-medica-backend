package com.topicos_especiais_1.clinica_medica.pagamentos.infra.gateway;

import org.springframework.stereotype.Component;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentPointOfInteraction;
import com.mercadopago.resources.payment.PaymentTransactionData;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class MercadoPagoGatewayImpl implements MercadoPagoGateway {

    @Override
    public PagamentoResponse pagarComCartao(CriarPagamentoRequest request) {
        try {
            PaymentClient client = new PaymentClient();

            PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                    .token(request.cardTokenId())
                    .transactionAmount(request.valor())
                    .description(request.descricao())
                    .installments(1)
                    .paymentMethodId("master")
                    .payer(PaymentPayerRequest.builder()
                            .email(request.payerEmail())
                            .build())
                    .build();

            Payment payment = client.create(paymentRequest);

            return new PagamentoResponse(
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    null,
                    null
            );

        } catch (MPException | MPApiException ex) { // Resolvido: Multicatch específico
            throw new RuntimeException("Erro ao processar pagamento com cartão: " + ex.getMessage(), ex);
        }
    }

    @Override
    public PagamentoResponse gerarPix(CriarPagamentoRequest request) {
        try {
            PaymentClient client = new PaymentClient();

            PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                    .transactionAmount(request.valor())
                    .description(request.descricao())
                    .paymentMethodId("pix")
                    .payer(PaymentPayerRequest.builder()
                            .email(request.payerEmail())
                            .build())
                    .build();

            Payment payment = client.create(paymentRequest);

            // Resolvido: Uso das classes fortemente tipadas do SDK moderno do Mercado Pago
            String qrCode = null;
            String qrCodeBase64 = null;

            PaymentPointOfInteraction point = payment.getPointOfInteraction();
            if (point != null && point.getTransactionData() != null) {
                PaymentTransactionData transactionData = point.getTransactionData();
                qrCode = transactionData.getQrCode();
                qrCodeBase64 = transactionData.getQrCodeBase64();
            }

            return new PagamentoResponse(
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    qrCode,
                    qrCodeBase64
            );

        } catch (MPException | MPApiException ex) { // Resolvido: Multicatch específico
            throw new RuntimeException("Erro ao gerar Pix no Mercado Pago: " + ex.getMessage(), ex);
        }
    }

    @Override
    public PagamentoResponse consultarPagamento(String paymentId) {
        try {
            PaymentClient client = new PaymentClient();

            Payment payment = client.get(Long.valueOf(paymentId));

            return new PagamentoResponse(
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    null,
                    null
            );

        } catch (MPException | MPApiException ex) { // Resolvido: Multicatch específico
            throw new RuntimeException("Erro ao consultar pagamento: " + ex.getMessage(), ex);
        }
    }
}
