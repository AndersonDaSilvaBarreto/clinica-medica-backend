package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.FilaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AtualizarStatusFilaRequest;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarStatusFilaUseCase {
    private final FilaAtendimentoRepository filaAtendimentoRepository;
    @Transactional
    public void execute(UUID filaId, AtualizarStatusFilaRequest request, Usuario usuario) {
        FilaAtendimento fila = filaAtendimentoRepository.buscarPorId(filaId);
        if(!Perfil.RECEPCIONISTA.equals(usuario.getPerfil()) && !fila.getMedico().getUsuario().equals(usuario)) {
            throw new AcessoNegadoException("Você não tem permissão de adicionar consulta na fila");
        }
        fila.mudarStatus(request.status());
        filaAtendimentoRepository.salvar(fila);

    }
}
