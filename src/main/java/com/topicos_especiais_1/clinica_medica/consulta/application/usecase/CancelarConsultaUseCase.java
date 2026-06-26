package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelarConsultaUseCase {
    private final ConsultaRepository consultaRepository;

    @Transactional
    public void execute(UUID consultaId, String motivo, Usuario usuarioAutenticado) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId);
        if(Perfil.PACIENTE.equals(usuarioAutenticado.getPerfil())) {
            if(!consulta.getPaciente().getUsuario().equals(usuarioAutenticado)) {
                throw new AcessoNegadoException("Ação não permitida. Você só pode cancelar suas próprias consultas.");
            }
        }else if (!Perfil.RECEPCIONISTA.equals(usuarioAutenticado.getPerfil()) && !Perfil.ADMINISTRADOR.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException("Seu perfil de usuário não tem permissão para cancelar consultas.");
        }
        consulta.cancelar(motivo,usuarioAutenticado);
        consultaRepository.salvar(consulta);
    }
}
