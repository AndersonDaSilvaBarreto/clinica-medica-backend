package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.FilaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FilaAtendimentoRepositoryImpl implements FilaAtendimentoRepository {
    private final SpringDataFilaAtendimento springDataFilaAtendimento;


    @Override
    public FilaAtendimento salvar(FilaAtendimento filaAtendimento) {
        return springDataFilaAtendimento.save(filaAtendimento);
    }

    @Override
    public FilaAtendimento buscarPorId(UUID filaAtendimentoId) {
        return springDataFilaAtendimento.findById(filaAtendimentoId).orElseThrow(() ->
                EntidadeNaoEncontradaException.porId("Fila Atendimento", filaAtendimentoId));
    }

    @Override
    public List<FilaAtendimento> buscaPaginada(Specification<FilaAtendimento> specs, Pageable pageable) {
        return springDataFilaAtendimento.findAll(specs,pageable).getContent();
    }

    @Override
    public boolean existeConsultaNaFilaDoDia(UUID consultaId, LocalDate data) {
         Specification<FilaAtendimento> specs = Specification
                .where(FilaAtendimentoSpecifications.porConsultaId(consultaId))
                .and(FilaAtendimentoSpecifications.porDataDia(data));
         return !springDataFilaAtendimento.findAll(specs, PageRequest.of(0,1)).isEmpty();
    }

}
