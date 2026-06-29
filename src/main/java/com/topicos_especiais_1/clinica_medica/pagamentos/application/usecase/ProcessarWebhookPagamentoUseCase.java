package com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase;

import java.util.Map;

public interface ProcessarWebhookPagamentoUseCase {

    void execute(Map<String, Object> payload);
}
