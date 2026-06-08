package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosUsuarioVerificacaoPendente;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.VerificacaoInvalidaException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.VerificacaoRegistroDto;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificarRegistroUseCase {
    private final UsuarioRepository repository;
    private final RedisService redisService;
    private final ApplicationEventPublisher eventPublisher;

    public void execute(VerificacaoRegistroDto dto) {
        String chave = "verificacao:" + dto.email();
        var dados = redisService.buscar(
                chave,
                DadosUsuarioVerificacaoPendente.class
        ).orElseThrow(
                () -> new VerificacaoInvalidaException(VerificacaoInvalidaException.CODIGO_INVALIDO)
        );
        if (!dados.equals(dto.codigo()))
            throw new VerificacaoInvalidaException(VerificacaoInvalidaException.CODIGO_INVALIDO);
        var novoUsuario = Usuario.createPaciente(
                Nome.of(dados.nome()),
                Email.of(dados.email()),
                Senha.ofHash(dados.senha()));
        repository.salvar(novoUsuario);
        eventPublisher.publishEvent(new UsuarioCriadoEvent(
                novoUsuario.getId().getValue(),
                novoUsuario.getPerfil()
        ));
    }
}
