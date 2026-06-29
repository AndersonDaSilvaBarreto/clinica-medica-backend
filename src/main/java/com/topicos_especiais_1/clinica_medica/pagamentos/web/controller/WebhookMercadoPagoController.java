package com.topicos_especiais_1.clinica_medica.pagamentos.web.controller;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase.ProcessarWebhookPagamentoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/webhooks/mercadopago")
@RequiredArgsConstructor
public class WebhookMercadoPagoController {

    private final ProcessarWebhookPagamentoUseCase processarWebhookPagamentoUseCase;

    @PostMapping
    public ResponseEntity<Void> receberWebhook(
            @RequestBody Map<String, Object> payload
    ) {

        log.info("Webhook Mercado Pago: {}", payload);

        try {
            processarWebhookPagamentoUseCase.execute(payload);
        } catch (Exception ex) {
            // Nunca deixamos uma falha de processamento devolver erro pro Mercado Pago,
            // pois isso faria ele reenviar a notificação indefinidamente.
            // O erro já fica registrado em log para investigação manual.
            log.error("Erro ao processar webhook do Mercado Pago: {}", payload, ex);
        }

        return ResponseEntity.ok().build();
    }
}
