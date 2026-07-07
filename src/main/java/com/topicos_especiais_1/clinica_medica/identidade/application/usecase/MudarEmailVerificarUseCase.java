package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.DadosMudarEmail;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.CodigoExpiradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.MudarEmailVerificarRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MudarEmailVerificarUseCase {
    private final UsuarioRepository usuarioRepository;
    private final RedisService redisService;

    @Transactional
    public void execute(MudarEmailVerificarRequest request, Usuario usuarioAutenticado) {
        Email email = Email.of(request.email());
        String chave = "MudarEmail:" + email;
        var dados = redisService.buscar(chave, DadosMudarEmail.class).orElseThrow(() -> new CodigoExpiradoException("Dados não encotrados"));
        Usuario usuario = usuarioRepository.buscarPorId(dados.usuarioId());
        if(!usuario.equals(usuarioAutenticado)) {
            throw new AcessoNegadoException("Você não tem autorização para este recurso");

        }
        if(!dados.codigo().equals(request.codigo())) {
            throw new CodigoExpiradoException("Codigo inválido");
        }
        usuario.mudarEmail(dados.email());
        usuarioRepository.salvar(usuario);

    }
}
