package com.topicos_especiais_1.clinica_medica.notificacoes.infra.email;


import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.service.NotificacaoService;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResendEmailService implements NotificacaoService {

    @Value("${resend.from}")
    private String from;

    private final Resend resend;

    public ResendEmailService(@Value("${resend.api-key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }
    @Override
    public void enviarEmail(@NonNull Email destinatario,@NonNull String assunto, String corpo) {

        CreateEmailOptions createEmailOptions = CreateEmailOptions.builder()
                .from(from)
                .to(destinatario.toString())
                .subject(assunto)
                .html(corpo)
                .build();
        try {
            resend.emails().send(createEmailOptions);
        } catch (ResendException e) {
            log.error("Falha ao enviar email para {}: {} - {}", destinatario, e.getErrorName(), e.getMessage());
        }

    }
}
