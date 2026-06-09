package com.topicos_especiais_1.clinica_medica.notificacoes.application.listener;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.api.event.VerificacaoSolicitadaEvent;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificacoesUsuarioEventListener {
    private final NotificacaoService notificacaoService;

    @ApplicationModuleListener
    public void onVerificacaoSolicitada(VerificacaoSolicitadaEvent event) {
        notificacaoService.enviarEmail(
                event.email(),
                "Código de verificação",
                "Código de verificação: " + event.codigo() );
    }
    @ApplicationModuleListener
    public void onVerificacaoConfirmada(UsuarioCriadoEvent event) {
        notificacaoService.enviarEmail(
                event.email(),
                "Cadastro concluído",
                "Seja bem vindo ao sistema da Sumed Clinica médica"
        );
    }
}
