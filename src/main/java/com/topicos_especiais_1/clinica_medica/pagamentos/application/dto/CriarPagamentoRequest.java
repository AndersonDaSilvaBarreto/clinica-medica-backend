package com.topicos_especiais_1.clinica_medica.pagamentos.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.enums.FormaPagamento;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CriarPagamentoRequest(

        @NotNull
        FormaPagamento formaPagamento,

        String cardTokenId,

        @NotNull
        UUID consultaId,

        @NotNull
        BigDecimal valor,

        @NotNull
        String descricao,

        @NotNull
        @Email
        String payerEmail,

        @NotNull
        String payerCpf

) {
}