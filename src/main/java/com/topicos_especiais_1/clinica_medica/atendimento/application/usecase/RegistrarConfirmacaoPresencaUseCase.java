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
        Recepcionista recepcionista = recepcionistaApi.buscarPorUsuarioId(usuario.getId());
        if(confirmacaoPresencaRepository.existePorConsultaId(consultaId)) {
            throw new ConflitoException("A presença na consulta já está confirmada");
        }
        consulta.mudarStatus(StatusConsulta.PRESENTE);
        ConfirmacaoPresenca.create(consulta,recepcionista);

    }
}
