package com.topicos_especiais_1.clinica_medica.pagamentos.infra.persistence;

import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPagamentoRepository
        extends JpaRepository<Pagamento, UUID> {

    Optional<Pagamento> findByConsultaId(
            UUID consultaId
    );
}