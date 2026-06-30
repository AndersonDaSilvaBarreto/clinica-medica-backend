package com.topicos_especiais_1.clinica_medica.pagamentos.infra.gateway;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MercadoPagoGatewayImpl
        implements MercadoPagoGateway {

    @Override
    public PagamentoResponse pagarComCartao(
            CriarPagamentoRequest request
    ) {

        try {

            PaymentClient client =
                    new PaymentClient();

            PaymentCreateRequest paymentRequest =
                    PaymentCreateRequest.builder()
                            .token(request.cardTokenId())
                            .transactionAmount(request.valor())
                            .description(request.descricao())
                            .installments(1)
                            .paymentMethodId("master")
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(
                                                    request.payerEmail()
                                            )
                                            .build()
                            )
                            .build();

            Payment payment =
                    client.create(paymentRequest);

            return new PagamentoResponse(
                    null,
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    null,
                    null
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex.getMessage()
            );
        }
    }

    @Override
    public PagamentoResponse gerarPix(
            CriarPagamentoRequest request
    ) {

        try {

            PaymentClient client =
                    new PaymentClient();

            PaymentCreateRequest paymentRequest =
                    PaymentCreateRequest.builder()
                            .transactionAmount(
                                    request.valor()
                            )
                            .description(
                                    request.descricao()
                            )
                            .paymentMethodId(
                                    "pix"
                            )
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(
                                                    request.payerEmail()
                                            )
                                            .build()
                            )
                            .build();

            Payment payment =
                    client.create(paymentRequest);

            com.mercadopago.resources.payment.PaymentPointOfInteraction point =
                    payment.getPointOfInteraction();

            String qrCode = null;
            String qrCodeBase64 = null;

            if (point != null && point.getTransactionData() != null) {
                qrCode = point.getTransactionData().getQrCode();
                qrCodeBase64 = point.getTransactionData().getQrCodeBase64();
            }

            return new PagamentoResponse(
                    null,
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    qrCode,
                    qrCodeBase64
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex.getMessage()
            );
        }
    }

    @Override
    public PagamentoResponse consultarPagamento(
            String paymentId
    ) {

        try {

            PaymentClient client =
                    new PaymentClient();

            Payment payment =
                    client.get(
                            Long.valueOf(paymentId)
                    );

            return new PagamentoResponse(
                    null,
                    String.valueOf(payment.getId()),
                    payment.getStatus(),
                    payment.getStatusDetail(),
                    null,
                    null
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex.getMessage()
            );
        }
    }
}