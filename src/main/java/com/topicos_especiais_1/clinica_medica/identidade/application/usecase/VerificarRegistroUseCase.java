package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosUsuarioVerificacaoPendente;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.CodigoExpiradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.VerificacaoInvalidaException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.VerificacaoRegistroDto;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificarRegistroUseCase {
    private final UsuarioRepository repository;
    private final RedisService redisService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(VerificacaoRegistroDto dto) {
        String chave = "verificacao:" + dto.email();
        var dados = redisService.buscar(
                chave,
                DadosUsuarioVerificacaoPendente.class
        ).orElseThrow(
                () -> new CodigoExpiradoException(VerificacaoInvalidaException.CODIGO_INVALIDO)
        );
        if (!dados.codigo().equals(dto.codigo()))
            throw new VerificacaoInvalidaException(VerificacaoInvalidaException.CODIGO_INVALIDO
            );
        var novoUsuario = Usuario.createPaciente(
                dados.nome(),
                dados.email(),
                dados.senha(),
                dados.genero(),
                dados.cpf(),
                dados.dataNascimento(),
                dados.telefone()
        );
        repository.salvar(novoUsuario);
        eventPublisher.publishEvent(new UsuarioCriadoEvent(
                novoUsuario.getId(),
                novoUsuario.getEmail(),
                novoUsuario.getPerfil(),
                dados.endereco()
        ));
    }
}
