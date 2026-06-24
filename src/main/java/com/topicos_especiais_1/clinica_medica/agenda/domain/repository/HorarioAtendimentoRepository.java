package com.topicos_especiais_1.clinica_medica.agenda.domain.repository;


import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioAtendimento;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import java.util.List;

public interface HorarioAtendimentoRepository {
    List<HorarioAtendimento> buscarPorMedico(Medico medico);

}
