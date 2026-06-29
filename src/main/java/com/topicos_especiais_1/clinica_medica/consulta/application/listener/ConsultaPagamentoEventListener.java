package com.topicos_especiais_1.clinica_medica.consulta.application.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.topicos_especiais_1.clinica_medica.consulta.api.event.ConsultaAgendadaEvent;
import com.topicos_especiais_1.clinica_medica.consulta.api.event.ConsultaPagamentoRecusadoEvent;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.pagamentos.api.event.PagamentoAprovadoEvent;
import com.topicos_especiais_1.clinica_medica.pagamentos.api.event.PagamentoRecusadoEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaPagamentoEventListener {

    private final ConsultaRepository consultaRepository;
    private final ApplicationEventPublisher eventPublisher;

    @ApplicationModuleListener
    public void onPagamentoAprovado(PagamentoAprovadoEvent event) {

        Consulta consulta = consultaRepository.buscarPorId(event.consultaId());

        consulta.confirmarPagamento(event.pagamentoId());

        consultaRepository.salvar(consulta);

        log.info("Consulta {} agendada após confirmação do pagamento {}.",
                consulta.getId(), event.pagamentoId());

        eventPublisher.publishEvent(new ConsultaAgendadaEvent(
                consulta.getId(),
                consulta.getPaciente().getUsuario().getId(),
                consulta.getPaciente().getUsuario().getEmail(),
                consulta.getDataHoraInicio()
        ));
    }

    @ApplicationModuleListener
    public void onPagamentoRecusado(PagamentoRecusadoEvent event) {

        Consulta consulta = consultaRepository.buscarPorId(event.consultaId());

        log.info("Pagamento da consulta {} foi recusado/cancelado: {}.",
                consulta.getId(), event.motivo());

        eventPublisher.publishEvent(new ConsultaPagamentoRecusadoEvent(
                consulta.getId(),
                consulta.getPaciente().getUsuario().getId(),
                consulta.getPaciente().getUsuario().getEmail(),
                event.motivo()
        ));
    }
}
