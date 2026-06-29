package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarStatusConsultaUseCase {

    private final ConsultaRepository consultaRepository;

    @Transactional
    public void execute(UUID consultaId, StatusConsulta novoStatus, Usuario usuarioAutenticado) {
        if (!Perfil.MEDICO.equals(usuarioAutenticado.getPerfil())
                && !Perfil.RECEPCIONISTA.equals(usuarioAutenticado.getPerfil())
                && !Perfil.ADMINISTRADOR.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException("Seu perfil não tem permissão para alterar o status de consultas.");
        }

        Consulta consulta = consultaRepository.buscarPorId(consultaId);
        consulta.mudarStatus(novoStatus);
        consultaRepository.salvar(consulta);
    }
}
