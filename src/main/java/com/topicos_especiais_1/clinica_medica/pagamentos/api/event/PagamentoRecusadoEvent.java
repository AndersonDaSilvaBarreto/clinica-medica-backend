package com.topicos_especiais_1.clinica_medica.pagamentos.api.event;

import java.util.UUID;

/**
 * Publicado quando um pagamento é confirmado como recusado/cancelado pelo
 * Mercado Pago (via webhook).
 */
public record PagamentoRecusadoEvent(
        UUID pagamentoId,
        UUID consultaId,
        String motivo
) {
}
