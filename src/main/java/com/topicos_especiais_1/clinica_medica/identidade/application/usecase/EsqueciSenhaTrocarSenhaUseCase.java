package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.application.dto.EsqueciSenhaDadosVerificadosDto;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.CodigoExpiradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.EsqueciSenhaTrocarSenhaRequest;
import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EsqueciSenhaTrocarSenhaUseCase {
    private final RedisService redisService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void execute(EsqueciSenhaTrocarSenhaRequest request) {
        String chave = "DadosEsqueciSenhaVerificado:" + request.chave();
        var dados = redisService.buscar(
                chave,
                EsqueciSenhaDadosVerificadosDto.class
        ).orElseThrow(() -> new CodigoExpiradoException("Troca de senha expirada"));
        Usuario usuario = usuarioRepository.buscarPorEmail(dados.email());
        Senha senha = Senha.of(request.senha());
        Senha senhaHasheada = Senha.ofHash(Objects.requireNonNull(passwordEncoder.encode(senha.getValue())));
        usuario.mudarSenha(senhaHasheada);
        usuarioRepository.salvar(usuario);
    }
}
