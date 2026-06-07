package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.VerificacaoSolicitadaEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosUsuarioVerificacaoPendente;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioExistenteException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.service.CodeGenerator;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto.RegisterDto;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComecarRegistroPacienteUseCase {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private static final Duration EXPIRACAO = Duration.ofMinutes(5);
    private final RedisTemplate<Object, Object> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public String execute(RegisterDto dto) {
        Nome nome = Nome.of(dto.name());
        Email email = Email.of(dto.email());
        Senha senha = Senha.of(dto.password());
        Senha senhaHasheada = Senha.ofHash(Objects.requireNonNull(passwordEncoder.encode(senha.getValue())));

        if(repository.existePorEmail(email)) throw new UsuarioExistenteException();
        String codigo = CodeGenerator.gerarCodigo();
        String chave = "verificacao:" + email;
        try {
            String json = objectMapper.writeValueAsString(
                    new DadosUsuarioVerificacaoPendente(
                            nome.toString(),
                            email.toString(),
                            senhaHasheada.toString(),
                            codigo
                    )
            );
            redisTemplate.opsForValue().set(chave, json, EXPIRACAO);
            eventPublisher.publishEvent(
                    new VerificacaoSolicitadaEvent(
                            email,
                            codigo
                    )
            );
            return codigo;
        } catch (JacksonException e) {
            throw new RuntimeException("Erro ao serializar dados");
        }


    }
}
