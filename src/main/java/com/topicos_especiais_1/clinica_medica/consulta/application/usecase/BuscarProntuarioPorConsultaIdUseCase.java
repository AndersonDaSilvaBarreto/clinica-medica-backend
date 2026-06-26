package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ProntuarioRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ProntuarioResponse;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarProntuarioPorConsultaIdUseCase {
    private final ProntuarioRepository prontuarioRepository;
    @Transactional(readOnly = true)
    public ProntuarioResponse execute(UUID consultaId, Usuario usuarioAutenticado) {
        Prontuario prontuario = prontuarioRepository.porConsultaId(consultaId);
        if(!prontuario.getMedico().getUsuario().equals(usuarioAutenticado)
                && !prontuario.getPaciente().getUsuario().equals(usuarioAutenticado)) {
            throw new AcessoNegadoException("Você não tem permissão de acesso");
        }
        return ProntuarioResponse.fromEntity(prontuario);
    }
}
