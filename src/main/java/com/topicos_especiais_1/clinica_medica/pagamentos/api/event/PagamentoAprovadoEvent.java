package com.topicos_especiais_1.clinica_medica.pagamentos.api.event;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Publicado quando um pagamento é confirmado como aprovado pelo Mercado Pago
 * (via webhook). Módulos como {@code consulta} e {@code notificacoes} reagem
 * a este evento para, respectivamente, agendar a consulta e notificar o paciente.
 */
public record PagamentoAprovadoEvent(
        UUID pagamentoId,
        UUID consultaId,
        BigDecimal valor
) {
}
