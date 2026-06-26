package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ProntuarioRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ProntuarioResponse;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.RegistrarProntuarioRequest;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoInvalidoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrarProntuarioUseCase {
    private final ConsultaRepository consultaRepository;
    private final ProntuarioRepository prontuarioRepository;

    @Transactional
    public ProntuarioResponse execute(
            UUID consultaId,
            RegistrarProntuarioRequest request,
            Usuario usuarioAutenticado) {
        if(!Perfil.MEDICO.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException("Apenas médicos podem registrar prontuários clínicos.");
        }
        Consulta consulta = consultaRepository.buscarPorId(consultaId);

        if (!consulta.getMedico().getUsuario().equals(usuarioAutenticado)) {
            throw new AcessoNegadoException("Você não tem permissão para registrar prontuários para consultas de outro profissional.");
        }
        if (consulta.getStatusConsulta() == StatusConsulta.CANCELADA) {
            throw FormatoInvalidoException.from("Consulta", "Não é possível registrar prontuário para uma consulta cancelada.");
        }
        if(prontuarioRepository.existePorConsulta(consulta)) {
            throw ConflitoException.of("Prontuario", "Já existe um prontuário médico registrado para esta consulta.");
        }

        Prontuario prontuario = Prontuario.builder()
                .consulta(consulta)
                .paciente(consulta.getPaciente())
                .medico(consulta.getMedico())
                .historico(request.historico())
                .receita(request.receita())
                .examesSolicitados(request.examesSolicitados())
                .build();
        consulta.mudarStatus(StatusConsulta.FINALIZADA);
        consultaRepository.salvar(consulta);
        Prontuario prontuarioSalvo = prontuarioRepository.salvar(prontuario);
        return ProntuarioResponse.fromEntity(prontuarioSalvo);
    }
}
