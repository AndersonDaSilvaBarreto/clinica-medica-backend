package com.topicos_especiais_1.clinica_medica.notificacoes.application.listener;

import com.topicos_especiais_1.clinica_medica.notificacoes.api.events.CriarNotificacaoEvent;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoEventListener {
    private final NotificacaoRepository notificacaoRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onNotificacaoCriadaEvent(
            CriarNotificacaoEvent event
    ) {
        Notificacao notificacao = Notificacao.create(event.usuario(),event.tipo(),event.mensagem());
        notificacaoRepository.salvar(notificacao);

    }
}
