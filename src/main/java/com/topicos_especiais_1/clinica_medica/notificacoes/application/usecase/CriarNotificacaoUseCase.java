package com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase;

import java.util.UUID;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.notificacoes.application.mapper.NotificacaoMapper;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.enums.TipoNotificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.service.NotificacaoService;
import com.topicos_especiais_1.clinica_medica.notificacoes.infra.sse.SseNotificacaoService;
import com.topicos_especiais_1.clinica_medica.notificacoes.web.dto.NotificacaoResponse;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Centraliza a criação de uma notificação: persiste no banco, envia para o
 * stream SSE do usuário (se ele estiver conectado) e dispara o e-mail
 * correspondente.
 */
@Service
@RequiredArgsConstructor
public class CriarNotificacaoUseCase {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoService notificacaoService;
    private final SseNotificacaoService sseNotificacaoService;

    @Transactional
    public void execute(
            UUID usuarioId,
            Email emailDestinatario,
            TipoNotificacao tipo,
            String mensagem,
            String assuntoEmail
    ) {

        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        Notificacao notificacao = Notificacao.criar(usuario, tipo, mensagem);

        notificacaoRepository.salvar(notificacao);

        NotificacaoResponse response = NotificacaoMapper.toResponse(notificacao);

        sseNotificacaoService.enviar(usuarioId, "notificacao", response);

        if (emailDestinatario != null) {
            notificacaoService.enviarEmail(emailDestinatario, assuntoEmail, mensagem);
        }
    }
}
