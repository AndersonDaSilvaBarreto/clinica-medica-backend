package com.topicos_especiais_1.clinica_medica.pagamentos.application.usecase;

import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.pagamentos.application.dto.ConsultarPagamentoResponse;
import com.topicos_especiais_1.clinica_medica.pagamentos.application.mapper.PagamentoMapper;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.entity.Pagamento;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.exception.PagamentoException;
import com.topicos_especiais_1.clinica_medica.pagamentos.domain.repository.PagamentoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarPagamentoUseCaseImpl implements ConsultarPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;

    @Override
    @Transactional(readOnly = true)
    public ConsultarPagamentoResponse execute(UUID pagamentoId) {

        Pagamento pagamento = pagamentoRepository
                .buscarPorId(pagamentoId)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));

        return PagamentoMapper.toResponse(pagamento);
    }
}
