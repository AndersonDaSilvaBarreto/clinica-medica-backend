package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ReagendamentoConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReagendamentoConsultaImpl implements ReagendamentoConsultaRepository {
    private final SpringDataReagendamento springDataReagendamento;
    @Override
    public ReagendamentoConsulta salvar(ReagendamentoConsulta reagendamentoConsulta) {
        return springDataReagendamento.save(reagendamentoConsulta);
    }
}
