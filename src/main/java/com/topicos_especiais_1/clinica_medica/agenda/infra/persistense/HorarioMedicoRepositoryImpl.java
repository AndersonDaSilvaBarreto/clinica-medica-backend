package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HorarioMedicoRepositoryImpl implements HorarioMedicoRepository {

    private final SpringDataHorarioMedico springData;

    @Override
    public HorarioMedico salvar(HorarioMedico horario) {
        return springData.save(horario);
    }

    @Override
    public void salvarTodos(List<HorarioMedico> horarios) {
        springData.saveAll(horarios);
    }

    @Override
    public Optional<HorarioMedico> buscarPorMedicoIdEDataHora(UUID medicoId, Instant dataHora) {
        return springData.findByMedicoIdAndDataHora(medicoId, dataHora);
    }

    @Override
    public List<HorarioMedico> buscarPorMedicoId(UUID medicoId) {
        return springData.findByMedicoIdOrderByDataHoraAsc(medicoId);
    }

    @Override
    public List<HorarioMedico> buscarPorMedicoIdEPeriodo(UUID medicoId, Instant inicio, Instant fim) {
        return springData.findByMedicoIdAndDataHoraBetweenOrderByDataHoraAsc(medicoId, inicio, fim);
    }

    @Override
    public Optional<Instant> buscarDataMaximaGerada(UUID medicoId) {
        return springData.findMaxDataHoraByMedicoId(medicoId);
    }

    
}
