package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.EsqueciSenhaCodigoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.EsqueciSenhaDadosVerificadosDto;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.CodigoExpiradoException;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.EsqueciSenhaVerificadoResponse;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.EsqueciSenhaVerificarCodigoRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EsqueciSenhaVerificarCodigoUseCase {
    private final RedisService redisService;

    @Transactional(readOnly = true)
    public EsqueciSenhaVerificadoResponse execute(EsqueciSenhaVerificarCodigoRequest request) {
        Email email = Email.of(request.email());
        String chave = "esqueciSenha:" + email.toString();
        EsqueciSenhaCodigoEvent dados = redisService.buscar(
                chave,
                EsqueciSenhaCodigoEvent.class

        ).orElseThrow(() -> EntidadeNaoEncontradaException.porEmail("Esqueci senha", email));

        if(!dados.codigo().equals(request.codigo())) {
            throw new CodigoExpiradoException("codigo expirado");
        }
        UUID chaveTrocaDeSenha = UUID.randomUUID();
        redisService.salvar(
                "DadosEsqueciSenhaVerificado:" + chaveTrocaDeSenha.toString(),
                new EsqueciSenhaDadosVerificadosDto(email),
                Duration.ofMinutes(5)
        );
        return new EsqueciSenhaVerificadoResponse(
                chaveTrocaDeSenha
        );

    }
}
