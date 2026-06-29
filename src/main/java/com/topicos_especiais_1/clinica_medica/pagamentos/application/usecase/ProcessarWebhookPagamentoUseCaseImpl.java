package com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase;

import java.util.Map;
import java.util.Optional;

import com.topicos_especiais_1.clinica_medica.pagamentos.api.event.PagamentoAprovadoEvent;
import com.topicos_especiais_1.clinica_medica.pagamentos.api.event.PagamentoRecusadoEvent;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.repository.PagamentoRepository;
import com.topicos_especiais_1.clinica_medica.pagamentos.infra.gateway.MercadoPagoGateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessarWebhookPagamentoUseCaseImpl implements ProcessarWebhookPagamentoUseCase {

    private final MercadoPagoGateway mercadoPagoGateway;
    private final PagamentoRepository pagamentoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(Map<String, Object> payload) {

        String paymentId = extrairPaymentId(payload);

        if (paymentId == null) {
            log.warn("Webhook do Mercado Pago ignorado: não foi possível extrair o paymentId. Payload: {}", payload);
            return;
        }

        PagamentoResponse statusAtual = mercadoPagoGateway.consultarPagamento(paymentId);

        Optional<Pagamento> pagamentoOpt = pagamentoRepository.buscarPorPaymentIdMp(paymentId);

        if (pagamentoOpt.isEmpty()) {
            log.warn("Webhook do Mercado Pago recebido para paymentId {} sem pagamento correspondente no banco.", paymentId);
            return;
        }

        Pagamento pagamento = pagamentoOpt.get();

        // Idempotência: se o pagamento já está em um estado final, não reprocessa
        // nem republica eventos (o Mercado Pago pode reenviar a mesma notificação).
        if (pagamento.estaFinalizado()) {
            log.info("Webhook do Mercado Pago ignorado: pagamento {} já está finalizado com status {}.",
                    pagamento.getId(), pagamento.getStatus());
            return;
        }

        switch (statusAtual.status()) {
            case "approved" -> {
                pagamento.aprovar(statusAtual.statusDetail());
                pagamentoRepository.salvar(pagamento);

                eventPublisher.publishEvent(new PagamentoAprovadoEvent(
                        pagamento.getId(),
                        pagamento.getConsultaId(),
                        pagamento.getValor()
                ));
            }
            case "rejected", "cancelled" -> {
                pagamento.recusar(statusAtual.statusDetail());
                pagamentoRepository.salvar(pagamento);

                eventPublisher.publishEvent(new PagamentoRecusadoEvent(
                        pagamento.getId(),
                        pagamento.getConsultaId(),
                        statusAtual.statusDetail()
                ));
            }
            default -> log.info("Webhook do Mercado Pago: pagamento {} ainda com status '{}', nada a fazer.",
                    pagamento.getId(), statusAtual.status());
        }
    }

    @SuppressWarnings("unchecked")
    private String extrairPaymentId(Map<String, Object> payload) {

        Object type = payload.get("type");

        if (type != null && !"payment".equals(type)) {
            return null;
        }

        Object data = payload.get("data");

        if (data instanceof Map<?, ?> dataMap) {
            Object id = dataMap.get("id");
            return id != null ? String.valueOf(id) : null;
        }

        return null;
    }
}
