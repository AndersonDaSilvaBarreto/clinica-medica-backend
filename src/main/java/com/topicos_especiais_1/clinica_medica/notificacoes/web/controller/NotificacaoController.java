package com.topicos_especiais_1.clinica_medica.notificacoes.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.notificacoes.web.dto.NotificacaoResponse;
import com.topicos_especiais_1.clinica_medica.notificacoes.application.mapper.NotificacaoMapper;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.exception.NotificacaoException;
import com.topicos_especiais_1.clinica_medica.notificacoes.infra.sse.SseNotificacaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoRepository notificacaoRepository;
    private final SseNotificacaoService sseNotificacaoService;

    /**
     * Mantém uma conexão Server-Sent Events aberta para o usuário autenticado.
     * O frontend deve consumir este endpoint via EventSource e escutar o
     * evento nomeado "notificacao".
     */
    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        return sseNotificacaoService.inscrever(usuarioAutenticado.usuario().getId());
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoResponse>> listar(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        List<NotificacaoResponse> notificacoes = notificacaoRepository
                .buscarPorUsuarioId(usuarioAutenticado.usuario().getId())
                .stream()
                .map(NotificacaoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/nao-lidas")
    public ResponseEntity<List<NotificacaoResponse>> listarNaoLidas(
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        List<NotificacaoResponse> notificacoes = notificacaoRepository
                .buscarNaoLidasPorUsuarioId(usuarioAutenticado.usuario().getId())
                .stream()
                .map(NotificacaoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(notificacoes);
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<Void> marcarComoLida(
            @PathVariable UUID id,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
    ) {
        Notificacao notificacao = notificacaoRepository
                .buscarPorUsuarioId(usuarioAutenticado.usuario().getId())
                .stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotificacaoException("Notificação não encontrada"));

        notificacao.marcarComoLida();
        notificacaoRepository.salvar(notificacao);

        return ResponseEntity.noContent().build();
    }
}
