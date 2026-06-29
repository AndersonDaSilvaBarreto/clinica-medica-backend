package com.topicos_especiais_1.clinica_medica.notificacoes.infra.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

/**
 * Mantém em memória, por usuário, as conexões SSE abertas no momento e permite
 * empurrar eventos de notificação para todas elas em tempo real.
 *
 * Funciona com uma única instância da aplicação. Em um cenário com múltiplas
 * instâncias, seria necessário substituir por um broker (ex.: Redis Pub/Sub)
 * para que a notificação chegue à instância correta.
 */
@Slf4j
@Component
public class SseNotificacaoService {

    private final Map<UUID, List<SseEmitter>> emittersPorUsuario = new ConcurrentHashMap<>();

    public SseEmitter inscrever(UUID usuarioId) {

        SseEmitter emitter = new SseEmitter(0L); // sem timeout: conexão fica aberta indefinidamente

        emittersPorUsuario
                .computeIfAbsent(usuarioId, id -> new CopyOnWriteArrayList<>())
                .add(emitter);

        emitter.onCompletion(() -> remover(usuarioId, emitter));
        emitter.onTimeout(() -> remover(usuarioId, emitter));
        emitter.onError(ex -> remover(usuarioId, emitter));

        return emitter;
    }

    public void enviar(UUID usuarioId, String eventName, Object payload) {

        List<SseEmitter> emitters = emittersPorUsuario.get(usuarioId);

        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        for (SseEmitter emitter : List.copyOf(emitters)) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(payload));
            } catch (IOException ex) {
                log.warn("Falha ao enviar SSE para usuário {}, removendo conexão.", usuarioId);
                remover(usuarioId, emitter);
            }
        }
    }

    private void remover(UUID usuarioId, SseEmitter emitter) {
        List<SseEmitter> emitters = emittersPorUsuario.get(usuarioId);
        if (emitters != null) {
            emitters.remove(emitter);
        }
    }
}
