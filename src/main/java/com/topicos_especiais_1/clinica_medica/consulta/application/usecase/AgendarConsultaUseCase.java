package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.BloqueioAgendaRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.HorarioMedicoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.domain.valueobject.DiaSemana;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.AgendarConsultaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.api.MedicoApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendarConsultaUseCase {

    private final ConsultaRepository consultaRepository;
    private final MedicoApi medicoApi;
    private final PacienteRepository pacienteRepository;
    private final BloqueioAgendaRepository bloqueioAgendaRepository;
    // ── NOVO ──────────────────────────────────────────────────────────────────
    private final HorarioMedicoRepository horarioMedicoRepository;
    // ─────────────────────────────────────────────────────────────────────────

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    @Transactional
    public void execute(AgendarConsultaRequest request, Usuario usuarioAutenticado) {
        Paciente paciente = pacienteRepository.buscarPorId(request.pacienteId());
        Medico medico = medicoApi.buscarPorIdComAgenda(request.medicoId());
        Instant inicio = request.dataHoraInicio();
        Instant fim = inicio.plus(Duration.ofMinutes(medico.getTempoConsultaMinutos()));

        LocalDate dataLocal = inicio.atZone(FUSO_HORARIO).toLocalDate();
        LocalTime horaInicioLocal = inicio.atZone(FUSO_HORARIO).toLocalTime();
        LocalTime horaFimLocal = fim.atZone(FUSO_HORARIO).toLocalTime();
        DayOfWeek dayOfWeekJava = inicio.atZone(FUSO_HORARIO).getDayOfWeek();
        DiaSemana diaSemanaDesejado = DiaSemana.de(dayOfWeekJava);

        if (Perfil.PACIENTE.equals(usuarioAutenticado.getPerfil())) {
            Paciente pacienteLogado = pacienteRepository.buscarPorUsuarioId(usuarioAutenticado.getId());
            if (!pacienteLogado.equals(paciente)) {
                throw new AcessoNegadoException("Ação não permitida. Um paciente só pode agendar consultas para si mesmo.");
            }
        } else if (!Perfil.RECEPCIONISTA.equals(usuarioAutenticado.getPerfil())
                && !Perfil.ADMINISTRADOR.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException("Seu perfil de usuário não tem permissão para realizar agendamentos.");
        }

        boolean medicoBloqueado = bloqueioAgendaRepository.existeBloqueioAtivoParaData(medico.getId(), dataLocal);
        if (medicoBloqueado) {
            throw ConflitoException.of("Consulta", "O médico não realizará atendimentos na data selecionada devido a bloqueio de agenda.");
        }

        boolean dentroDoHorarioDeTrabalho = medico.getHorariosAtendimento().stream()
                .anyMatch(h -> h.getDiaSemana() == diaSemanaDesejado
                        && !horaInicioLocal.isBefore(h.getHoraInicio())
                        && !horaFimLocal.isAfter(h.getHoraFim()));

        if (!dentroDoHorarioDeTrabalho) {
            throw ConflitoException.of("Medico Agenda", "O horário selecionado está fora do expediente de atendimento cadastrado para este médico.");
        }

        boolean conflitoHorarioConsulta = consultaRepository.existeConflitoHorarioMedico(medico.getId(), inicio, fim);
        if (conflitoHorarioConsulta) {
            throw ConflitoException.of("Consulta", "O médico escolhido já possui um agendamento neste horário.");
        }

        Consulta novaConsulta = Consulta.create(paciente, medico, inicio, fim, request.observacao(), usuarioAutenticado);
        consultaRepository.salvar(novaConsulta);

        // ── NOVO: marcar o slot correspondente como OCUPADO ───────────────────
        horarioMedicoRepository
                .buscarPorMedicoIdEDataHora(medico.getId(), inicio)
                .ifPresent(slot -> {
                    slot.marcarOcupado();
                    horarioMedicoRepository.salvar(slot);
                });
        // ─────────────────────────────────────────────────────────────────────
    }
}
