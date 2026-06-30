package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FinalizarAtendimentoUseCase {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;

    @Transactional
    public void execute(UUID consultaId, Usuario usuario) {
        if (!Perfil.MEDICO.equals(usuario.getPerfil())) {
            throw new AcessoNegadoException(
                "Apenas médicos podem finalizar o atendimento.");
        }

        Consulta consulta = consultaRepository.buscarPorId(consultaId);
        Medico medico = medicoRepository.buscarPorUsuarioId(usuario.getId());

        if (!consulta.getMedico().getId().equals(medico.getId())) {
            throw new AcessoNegadoException(
                "Você não tem permissão para finalizar consultas de outro médico.");
        }

        if (consulta.getStatusConsulta() != StatusConsulta.EM_ATENDIMENTO) {
            throw FormatoInvalidoException.from(
                "Consulta",
                "Apenas consultas EM_ATENDIMENTO podem ser finalizadas por este endpoint. " +
                "Status atual: " + consulta.getStatusConsulta()
            );
        }

        consulta.mudarStatus(StatusConsulta.FINALIZADA);
        consultaRepository.salvar(consulta);
    }
}
