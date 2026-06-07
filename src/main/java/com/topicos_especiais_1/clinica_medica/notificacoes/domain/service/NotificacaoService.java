package com.topicos_especiais_1.clinica_medica.notificacoes.domain.service;

public interface NotificacaoService {
    void enviarEmail(String destinatario, String assunto, String corpo);
}
