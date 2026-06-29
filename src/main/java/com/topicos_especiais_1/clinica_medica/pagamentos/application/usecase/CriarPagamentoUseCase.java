package com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.CriarPagamentoRequest;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.PagamentoResponse;

public interface CriarPagamentoUseCase {

    PagamentoResponse execute(
            CriarPagamentoRequest request
    );
}