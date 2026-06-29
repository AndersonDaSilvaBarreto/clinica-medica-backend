package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.HorarioMedico;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ReagendamentoConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoEmMassaResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoEmMassaResponse.ItemNaoReagendado;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ReagendamentoEmMassaResponse.ItemReagendado;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReagendamentoEmMassaUseCase {

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(FUSO_HORARIO);

    private final ConsultaRepository consultaRepository;
    private final HorarioMedicoRepository horarioMedicoRepository;
    private final ReagendamentoConsultaRepository reagendamentoConsultaRepository;
    private final MedicoRepository medicoRepository;

    @Transactional
    public ReagendamentoEmMassaResponse execute(
            UUID medicoId,
            LocalDate dataInicio,
            LocalDate dataFim,       // dataFim >= dataInicio; pode ser igual (dia único)
            String motivo,
            Usuario usuarioAutenticado
    ) {
        // 1. Valida existência do médico
        var medico = medicoRepository.buscarPorId(medicoId);

        // 2. Monta a janela completa do período (início do primeiro dia → fim do último dia)
        Instant inicioPeriodo = dataInicio.atStartOfDay(FUSO_HORARIO).toInstant();
        Instant fimPeriodo    = dataFim.plusDays(1).atStartOfDay(FUSO_HORARIO).toInstant();

        // 3. Todas as consultas ativas do médico no período inteiro
        List<Consulta> consultasDoPeriodo =
                consultaRepository.buscarConsultasAtivasPorMedicoEData(medicoId, inicioPeriodo, fimPeriodo);

        if (consultasDoPeriodo.isEmpty()) {
            return new ReagendamentoEmMassaResponse(0, 0, List.of(), List.of());
        }

        // 4. Próximos slots disponíveis a partir do dia seguinte ao fim do período
        Instant buscaAPartirDe = Instant.now().isAfter(fimPeriodo) ? Instant.now() : fimPeriodo;

        List<HorarioMedico> slotsDisponiveis = horarioMedicoRepository
                .buscarPorMedicoIdEPeriodo(medicoId, buscaAPartirDe, buscaAPartirDe.plus(Duration.ofDays(90)))
                .stream()
                .filter(HorarioMedico::isDisponivel)
                .sorted(java.util.Comparator.comparing(HorarioMedico::getDataHora))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        // 5. Aloca cada consulta no próximo slot disponível
        List<ItemReagendado>    reagendadas    = new ArrayList<>();
        List<ItemNaoReagendado> naoReagendadas = new ArrayList<>();

        for (Consulta consulta : consultasDoPeriodo) {
            if (slotsDisponiveis.isEmpty()) {
                naoReagendadas.add(new ItemNaoReagendado(
                        consulta.getId(),
                        "Nenhum horário disponível encontrado nos próximos 90 dias."
                ));
                continue;
            }

            HorarioMedico slot = slotsDisponiveis.remove(0);

            Instant novoInicio = slot.getDataHora();
            Instant novoFim    = novoInicio.plus(Duration.ofMinutes(medico.getTempoConsultaMinutos()));

            try {
                Instant inicioAntigo = consulta.getDataHoraInicio();
                Instant fimAntigo    = consulta.getDataHoraFim();

                consulta.reagendar(novoInicio, novoFim, motivo, usuarioAutenticado);
                consultaRepository.salvar(consulta);

                ReagendamentoConsulta historico = ReagendamentoConsulta.builder()
                        .consulta(consulta)
                        .dataHoraInicioAntiga(inicioAntigo)
                        .dataHoraFimAntiga(fimAntigo)
                        .dataHoraInicioNova(novoInicio)
                        .dataHoraFimNova(novoFim)
                        .motivo(motivo)
                        .reagendadoPor(usuarioAutenticado)
                        .build();
                reagendamentoConsultaRepository.salvar(historico);

                horarioMedicoRepository
                        .buscarPorMedicoIdEDataHora(medicoId, inicioAntigo)
                        .ifPresent(s -> {
                            s.marcarDisponivel();
                            horarioMedicoRepository.salvar(s);
                        });

                slot.marcarOcupado();
                horarioMedicoRepository.salvar(slot);

                reagendadas.add(new ItemReagendado(consulta.getId(), FMT.format(novoInicio)));

                log.info("Consulta {} reagendada de {} para {}",
                        consulta.getId(), FMT.format(inicioAntigo), FMT.format(novoInicio));

            } catch (Exception e) {
                log.warn("Falha ao reagendar consulta {}: {}", consulta.getId(), e.getMessage());
                slotsDisponiveis.add(0, slot);
                naoReagendadas.add(new ItemNaoReagendado(consulta.getId(), e.getMessage()));
            }
        }

        return new ReagendamentoEmMassaResponse(
                reagendadas.size(),
                naoReagendadas.size(),
                reagendadas,
                naoReagendadas
        );
    }
}
