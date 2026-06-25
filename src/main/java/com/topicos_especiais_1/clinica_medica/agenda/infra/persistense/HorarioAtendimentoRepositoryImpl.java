package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HorarioAtendimentoRepositoryImpl implements HorarioAtendimentoRepository {

    @Override
    public List<HorarioAtendimento> buscarPorMedico(Medico medico) {
        return List.of();
    }
}
