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
public class MarcarAusenteUseCase {

    private final FilaAtendimentoRepository filaAtendimentoRepository;
    private final ConsultaRepository consultaRepository;

    @Transactional
    public void execute(UUID filaId, Usuario usuario) {
        FilaAtendimento fila = filaAtendimentoRepository.buscarPorId(filaId);

        if (!Perfil.MEDICO.equals(usuario.getPerfil())
                && !Perfil.RECEPCIONISTA.equals(usuario.getPerfil())) {
            throw new AcessoNegadoException(
                "Apenas médico ou recepcionista podem marcar ausência.");
        }

        if (fila.getStatus() != StatusFila.CHAMADO) {
            throw FormatoInvalidoException.from(
                "FilaAtendimento",
                "Só é possível marcar ausência de pacientes que foram chamados. " +
                "Status atual: " + fila.getStatus()
            );
        }

        // Fila → AUSENTE
        fila.mudarStatus(StatusFila.AUSENTE);
        filaAtendimentoRepository.salvar(fila);

        // Consulta → FALTOU
        Consulta consulta = consultaRepository.buscarPorId(fila.getConsulta().getId());
        consulta.mudarStatus(StatusConsulta.FALTOU);
        consultaRepository.salvar(consulta);
    }
}
