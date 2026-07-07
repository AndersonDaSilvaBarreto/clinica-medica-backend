package com.topicos_especiais_1.clinica_medica.notificacoes.application.listener;

import com.topicos_especiais_1.clinica_medica.consulta.api.event.ConsultaAgendadaEvent;
import com.topicos_especiais_1.clinica_medica.consulta.api.event.ConsultaPagamentoRecusadoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.api.event.EsqueciSenhaCodigoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.api.event.VerificacaoSolicitadaEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosMudarEmail;
import com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase.CriarNotificacaoUseCase;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.enums.TipoNotificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class NotificacoesUsuarioEventListener {
    private final NotificacaoService notificacaoService;
    private final CriarNotificacaoUseCase criarNotificacaoUseCase;

    private static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

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
    @ApplicationModuleListener
    public void onEsqueciSenha(EsqueciSenhaCodigoEvent event) {
        System.out.println("chegou no listener");
        notificacaoService.enviarEmail(
                event.email(),
                "Codigo para trocar de senha",
                "Codigo : " + event.codigo()
        );
    }

    @ApplicationModuleListener
    public void onConsultaAgendada(ConsultaAgendadaEvent event) {

        String dataFormatada = event.dataHoraInicio()
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .format(FORMATO_DATA_HORA);

        String mensagem = "Seu pagamento foi aprovado e sua consulta para o dia "
                + dataFormatada + " está confirmada.";

        criarNotificacaoUseCase.execute(
                event.pacienteUsuarioId(),
                event.pacienteEmail(),
                TipoNotificacao.CONSULTA_AGENDADA,
                mensagem,
                "Consulta confirmada"
        );
    }

    @ApplicationModuleListener
    public void onPagamentoRecusado(ConsultaPagamentoRecusadoEvent event) {

        String mensagem = "Não conseguimos confirmar o pagamento da sua consulta. "
                + "Por favor, tente novamente.";

        criarNotificacaoUseCase.execute(
                event.pacienteUsuarioId(),
                event.pacienteEmail(),
                TipoNotificacao.PAGAMENTO_RECUSADO,
                mensagem,
                "Pagamento não aprovado"
        );
    }
    @ApplicationModuleListener
    public void onMudarEmail(DadosMudarEmail event) {
        notificacaoService.enviarEmail(
                event.email(),
                "Codigo para mudar email",
                "codigo: " + event.codigo()
        );
    }
}
