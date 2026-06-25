package com.topicos_especiais_1.clinica_medica.pagamentos.application.mapper;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.ConsultarPagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;

public class PagamentoMapper {

    private PagamentoMapper() {}

    public static ConsultarPagamentoResponse toResponse(
            Pagamento pagamento
    ) {

        return new ConsultarPagamentoResponse(
                pagamento.getId(),
                pagamento.getConsultaId(),
                pagamento.getFormaPagamento(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getPaymentIdMp(),
                pagamento.getStatusDetail()
        );
    }
}