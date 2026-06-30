package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.ConfirmacaoPresenca;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.ConfirmacaoPresencaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.api.RecepcionistaApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrarConfirmacaoPresencaUseCase {

    private final ConfirmacaoPresencaRepository confirmacaoPresencaRepository;
    private final ConsultaRepository consultaRepository;
    private final RecepcionistaApi recepcionistaApi;

    @Transactional
    public void execute(UUID consultaId, Usuario usuario) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId);

        // [BUG FIX] Confirmação de presença só é permitida se a consulta estiver AGENDADA.
        // Status como AGUARDANDO_PAGAMENTO, CANCELADA, FINALIZADA etc. devem ser rejeitados.
        if (consulta.getStatusConsulta() != StatusConsulta.AGENDADA) {
            throw FormatoInvalidoException.from(
                "Consulta",
                "A confirmação de presença só é permitida para consultas com status AGENDADA. " +
                "Status atual: " + consulta.getStatusConsulta()
            );
        }

        // [BUG FIX] Bloco de verificação de duplicidade mantido (estava correto).
        if (confirmacaoPresencaRepository.existePorConsultaId(consultaId)) {
            throw new ConflitoException("A presença nesta consulta já foi confirmada.");
        }

        Recepcionista recepcionista = recepcionistaApi.buscarPorUsuarioId(usuario.getId());

        // Muda o status da consulta para PRESENTE
        consulta.mudarStatus(StatusConsulta.PRESENTE);
        consultaRepository.salvar(consulta);

        // [BUG FIX] Persistia o ConfirmacaoPresenca via factory mas sem salvar no repositório.
        ConfirmacaoPresenca confirmacao = ConfirmacaoPresenca.create(consulta, recepcionista);
        confirmacaoPresencaRepository.salvar(confirmacao);
    }
}
