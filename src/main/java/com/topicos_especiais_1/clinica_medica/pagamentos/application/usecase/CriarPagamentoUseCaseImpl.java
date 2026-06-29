
package com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.FormaPagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.StatusPagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.exception.PagamentoException;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.repository.PagamentoRepository;
import com.topicos_especiais_1.clinica_medica.pagamentos.infra.gateway.MercadoPagoGateway;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarPagamentoUseCaseImpl implements CriarPagamentoUseCase {

    private final MercadoPagoGateway mercadoPagoGateway;
    private final PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public PagamentoResponse execute(CriarPagamentoRequest request) {

        if (request.formaPagamento() == FormaPagamento.CARTAO
                && (request.cardTokenId() == null || request.cardTokenId().isBlank())) {
            throw new PagamentoException("cardTokenId é obrigatório para pagamento com cartão");
        }

        PagamentoResponse gatewayResponse =
                request.formaPagamento() == FormaPagamento.CARTAO
                        ? mercadoPagoGateway.pagarComCartao(request)
                        : mercadoPagoGateway.gerarPix(request);

        Pagamento pagamento = new Pagamento(
                request.consultaId(),
                request.formaPagamento(),
                mapStatus(gatewayResponse.status()),
                request.valor(),
                gatewayResponse.paymentId(),
                gatewayResponse.statusDetail(),
                gatewayResponse.qrCode(),
                gatewayResponse.qrCodeBase64()
        );

        pagamentoRepository.salvar(pagamento);

        return gatewayResponse;
    }

    private StatusPagamento mapStatus(String statusMercadoPago) {

        if (statusMercadoPago == null) {
            return StatusPagamento.PENDENTE;
        }

        return switch (statusMercadoPago) {
            case "approved" -> StatusPagamento.APROVADO;
            case "rejected" -> StatusPagamento.RECUSADO;
            case "cancelled" -> StatusPagamento.CANCELADO;
            default -> StatusPagamento.PENDENTE;
        };
    }
}
