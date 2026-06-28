package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.EsqueciSenhaCodigoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.service.CodeGenerator;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.EsqueciSenhaRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EsqueciSenhaUseCase {
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisService redisService;

    @Transactional(readOnly = true)
    public void execute(EsqueciSenhaRequest request) {
        String codigo = CodeGenerator.gerarCodigo();
        Email email = Email.of(request.email());
        String chave = "esqueciSenha:" + email.toString();
        var dados = new EsqueciSenhaCodigoEvent(
                email,
                codigo
        );
        redisService.salvar(
                chave,
                dados,
                Duration.ofMinutes(5)
        );
        applicationEventPublisher.publishEvent(dados);

    }

}
