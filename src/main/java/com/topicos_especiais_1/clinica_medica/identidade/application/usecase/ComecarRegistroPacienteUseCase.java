package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.VerificacaoSolicitadaEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosUsuarioVerificacaoPendente;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioExistenteException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.service.CodeGenerator;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.RegisterDto;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComecarRegistroPacienteUseCase {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisService redisService;

    @Transactional(readOnly = true)
    public void execute(RegisterDto dto) {
        Nome nome = Nome.of(dto.nome());
        Email email = Email.of(dto.email());
        Senha senha = Senha.of(dto.senha());
        Senha senhaHasheada = Senha.ofHash(Objects.requireNonNull(passwordEncoder.encode(senha.getValue())));

        if (repository.existePorEmail(email)) throw new UsuarioExistenteException();
        String codigo = CodeGenerator.gerarCodigo();
        String chave = "verificacao:" + email;

        var dadosPendentes = new DadosUsuarioVerificacaoPendente(
                nome,
                email,
                senhaHasheada,
                dto.genero(),
                Cpf.of(dto.cpf()),
                dto.dataNascimento() != null ? DataNascimento.of(dto.dataNascimento()) : null
                ,
                codigo
        );

        redisService.salvar(
                chave,
                dadosPendentes,
                Duration.ofMinutes(5)
        );

        eventPublisher.publishEvent(
                new VerificacaoSolicitadaEvent(
                        email,
                        codigo
                )
        );
    }
}
