package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosMudarEmail;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.service.CodeGenerator;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.MudarEmailRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MudarEmailUseCase {
    private final UsuarioRepository usuarioRepository;
    private final RedisService redisService;
    private final ApplicationEventPublisher eventPublisher;
    @Transactional
    public void execute(MudarEmailRequest request, Usuario usuarioAutenticado) {
        Email email = Email.of(request.email());
        if(usuarioRepository.existePorEmail(email)) {
            throw ConflitoException.of("Usuario", "Email já cadastrado no sistema");
        }
        DadosMudarEmail dados = new DadosMudarEmail(usuarioAutenticado.getId(),email, CodeGenerator.gerarCodigo());
        String chave = "MudarEmail:" + email;
        redisService.salvar(
                chave,
                dados,
                Duration.ofMinutes(5)
        );
        eventPublisher.publishEvent(dados);
    }
}
