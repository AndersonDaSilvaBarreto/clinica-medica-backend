package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.StatusFila;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.FilaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ConfirmarComparecimentoUseCase {

    private final FilaAtendimentoRepository filaAtendimentoRepository;
    private final ConsultaRepository consultaRepository;

    @Transactional
    public void execute(UUID filaId, Usuario usuario) {
        FilaAtendimento fila = filaAtendimentoRepository.buscarPorId(filaId);

        // Apenas MEDICO dono da fila pode confirmar comparecimento
        if (!Perfil.MEDICO.equals(usuario.getPerfil())
                || !fila.getMedico().getUsuario().equals(usuario)) {
            throw new AcessoNegadoException(
                "Apenas o médico responsável pode confirmar o comparecimento do paciente.");
        }


        if (fila.getStatus() != StatusFila.CHAMADO) {
            throw FormatoInvalidoException.from(
                "FilaAtendimento",
                "O paciente precisa ter sido chamado antes de confirmar o comparecimento. " +
                "Status atual da fila: " + fila.getStatus()
            );
        }

        // Fila → ATENDIDO
        fila.finalizarAtendimento();
        filaAtendimentoRepository.salvar(fila);

        // Consulta → EM_ATENDIMENTO
        Consulta consulta = consultaRepository.buscarPorId(fila.getConsulta().getId());
        if (consulta.getStatusConsulta() != StatusConsulta.PRESENTE) {
            throw FormatoInvalidoException.from(
                "Consulta",
                "A consulta precisa estar com status PRESENTE para confirmar o atendimento. " +
                "Status atual: " + consulta.getStatusConsulta()
            );
        }
        consulta.mudarStatus(StatusConsulta.EM_ATENDIMENTO);
        consultaRepository.salvar(consulta);
    }
}
