package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.ReagendamentoConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ReagendamentoConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReagendarConsultaUseCase {
    private final ConsultaRepository consultaRepository;
    private final BloqueioAgendaRepository bloqueioAgendaRepository;
    private final HorarioMedicoRepository horarioMedicoRepository;
    private final ReagendamentoConsultaRepository reagendamentoConsultaRepository;
    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    @Transactional
    public void execute(UUID consultaId, Instant novoInicio, String motivo, Usuario usuarioAutenticado) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId);
        Medico medico = consulta.getMedico();

        if (Perfil.PACIENTE.equals(usuarioAutenticado.getPerfil())) {
            if (!consulta.getPaciente().getUsuario().getId().equals(usuarioAutenticado.getId())) {
                throw new AcessoNegadoException("Ação não permitida. Você só pode reagendar suas próprias consultas.");
            }
        } else if (!Perfil.RECEPCIONISTA.equals(usuarioAutenticado.getPerfil()) && !Perfil.ADMINISTRADOR.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException("Seu perfil de usuário não tem permissão para reagendar consultas.");
        }

        Instant inicioAntigo = consulta.getDataHoraInicio();
        Instant fimAntigo = consulta.getDataHoraFim();

        Instant novoFim = novoInicio.plus(Duration.ofMinutes(medico.getTempoConsultaMinutos()));
        LocalDate dataLocal = novoInicio.atZone(FUSO_HORARIO).toLocalDate();
        LocalTime horaInicioLocal = novoInicio.atZone(FUSO_HORARIO).toLocalTime();
        LocalTime horaFimLocal = novoFim.atZone(FUSO_HORARIO).toLocalTime();
        DiaSemana diaSemanaDesejado = DiaSemana.de(novoInicio.atZone(FUSO_HORARIO).getDayOfWeek());


        if (bloqueioAgendaRepository.existeBloqueioAtivoParaData(medico.getId(), dataLocal)) {
            throw ConflitoException.of("Consulta", "O médico não realizará atendimentos na data selecionada devido a bloqueio de agenda.");
        }


        boolean dentroDoHorarioDeTrabalho = medico.getHorariosAtendimento().stream()
                .anyMatch(h -> h.getDiaSemana() == diaSemanaDesejado &&
                        !horaInicioLocal.isBefore(h.getHoraInicio()) &&
                        !horaFimLocal.isAfter(h.getHoraFim()));

        if (!dentroDoHorarioDeTrabalho) {
            throw ConflitoException.of("Medico Agenda", "O horário selecionado está fora do expediente de atendimento cadastrado para este médico.");
        }

        boolean conflitoHorarioConsulta = consultaRepository.existeConflitoHorarioMedicoIgnorandoConsulta(
                medico.getId(), novoInicio, novoFim, consulta.getId()
        );
        if (conflitoHorarioConsulta) {
            throw ConflitoException.of("Consulta", "O médico escolhido já possui um agendamento neste horário.");
        }

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
                .buscarPorMedicoIdEDataHora(consulta.getMedico().getId(), inicioAntigo)
                .ifPresent(slot -> {
                    slot.marcarDisponivel();
                    horarioMedicoRepository.salvar(slot);
                });

        horarioMedicoRepository
                .buscarPorMedicoIdEDataHora(consulta.getMedico().getId(), novoInicio)
                .ifPresent(slot -> {
                    slot.marcarOcupado();
                    horarioMedicoRepository.salvar(slot);
                });

    }
}
