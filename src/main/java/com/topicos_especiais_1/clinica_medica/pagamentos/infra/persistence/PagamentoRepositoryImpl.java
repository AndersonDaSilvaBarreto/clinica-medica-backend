package com.topicos_especiais_1.clinica_medica.pagamentos.infra.persistence;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PagamentoRepositoryImpl
        implements PagamentoRepository {

    private final SpringDataPagamentoRepository repository;

    @Override
    public Pagamento salvar(
            Pagamento pagamento
    ) {
        return repository.save(
                pagamento
        );
    }

    @Override
    public Optional<Pagamento> buscarPorId(
            UUID id
    ) {
        return repository.findById(id);
    }

    @Override
    public Optional<Pagamento> buscarPorConsultaId(
            UUID consultaId
    ) {
        return repository.findByConsultaId(
                consultaId
        );
    }

    @Override
    public Optional<Pagamento> buscarPorPaymentIdMp(
            String paymentIdMp
    ) {
        return repository.findByPaymentIdMp(
                paymentIdMp
        );
    }
}