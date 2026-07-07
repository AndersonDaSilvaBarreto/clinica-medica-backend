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

        if (!Perfil.MEDICO.equals(usuarioAutenticado.getPerfil())) {
            throw new AcessoNegadoException(
                "Apenas médicos podem registrar prontuários clínicos.");
        }

        Consulta consulta = consultaRepository.buscarPorId(consultaId);

        if (!consulta.getMedico().getUsuario().equals(usuarioAutenticado)) {
            throw new AcessoNegadoException(
                "Você não tem permissão para registrar prontuários de consultas de outro profissional.");
        }

        if (consulta.getStatusConsulta() != StatusConsulta.EM_ATENDIMENTO) {
            throw FormatoInvalidoException.from(
                "Consulta",
                "O prontuário só pode ser registrado para consultas com status EM_ATENDIMENTO. " +
                "Status atual: " + consulta.getStatusConsulta()
            );
        }

        Prontuario prontuario;
        if (prontuarioRepository.existePorConsulta(consulta)) {
            prontuario = prontuarioRepository.porConsultaId(consultaId);
            prontuario.setHistorico(request.historico());
            prontuario.setReceita(request.receita());
            prontuario.setExamesSolicitados(request.examesSolicitados());
        } else {
            prontuario = Prontuario.builder()
                    .consulta(consulta)
                    .paciente(consulta.getPaciente())
                    .medico(consulta.getMedico())
                    .historico(request.historico())
                    .receita(request.receita())
                    .examesSolicitados(request.examesSolicitados())
                    .build();
        }

        Prontuario prontuarioSalvo = prontuarioRepository.salvar(prontuario);
        return ProntuarioResponse.fromEntity(prontuarioSalvo);
    }
}
