package com.topicos_especiais_1.clinica_medica.consulta.web.dto;

import java.util.List;
import java.util.UUID;

public record ReagendamentoEmMassaResponse(
        int totalReagendadas,
        int totalNaoReagendadas,
        List<ItemReagendado> reagendadas,
        List<ItemNaoReagendado> naoReagendadas
) {

    public record ItemReagendado(
            UUID consultaId,
            String novoHorario
    ) {}

    public record ItemNaoReagendado(
            UUID consultaId,
            String motivo
    ) {}
}
