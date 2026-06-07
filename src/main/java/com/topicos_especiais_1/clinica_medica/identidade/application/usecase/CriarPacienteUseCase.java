package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioExistenteException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.identidade.infra.web.dto.RegisterDto;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CriarPacienteUseCase {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void execute(RegisterDto dto) {
        Nome nome = Nome.of(dto.nome());
        Email email = Email.of(dto.email());
        Senha senha = Senha.of(dto.senha());
        Senha senhaHasheada = Senha.ofHash(Objects.requireNonNull(passwordEncoder.encode(senha.getValue())));

        boolean usuarioExiste = repository.existePorEmail(email);
        if(usuarioExiste) {
            throw new UsuarioExistenteException();
        }
        Usuario novoUsuario = Usuario.createPaciente(nome,email,senhaHasheada);
        if(dto.telefone() != null) {
            Telefone telefone = Telefone.of(dto.telefone());
            novoUsuario.mudarTelefone(telefone);
        }
        repository.salvar(novoUsuario);

    }
}
