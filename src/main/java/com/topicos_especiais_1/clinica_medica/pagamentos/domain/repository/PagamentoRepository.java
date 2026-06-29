package com.topicos_especiais_1.clinica_medica.pagamentos.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;

public interface PagamentoRepository {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorId(UUID id);

    Optional<Pagamento> buscarPorConsultaId(UUID consultaId);

    Optional<Pagamento> buscarPorPaymentIdMp(String paymentIdMp);
}