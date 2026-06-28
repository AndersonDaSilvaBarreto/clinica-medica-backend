package com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacaoLidaUseCase {
    private final NotificacaoRepository notificacaoRepository;

    @Transactional
    public void execute(UUID notificacaoId, Usuario usuarioAutenticado) {
        Notificacao notificacao = notificacaoRepository.buscarPorId(notificacaoId);
        if(!notificacao.getUsuario().equals(usuarioAutenticado)) {
            throw new AcessoNegadoException("Você não tem permissão de acesso");
        }
        notificacao.notificacaoLida();
        notificacaoRepository.salvar(notificacao);
    }
}
