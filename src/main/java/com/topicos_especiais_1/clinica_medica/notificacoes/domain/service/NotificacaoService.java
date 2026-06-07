package com.topicos_especiais_1.clinica_medica.notificacoes.domain.service;

import com.topicos_especiais_1.clinica_medica.shared.domain.Email;

public interface NotificacaoService {
    void enviarEmail(Email destinatario, String assunto, String corpo);
}
