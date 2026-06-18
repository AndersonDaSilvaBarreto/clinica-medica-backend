package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioApi {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario criarFuncionario(
            String nome,
            Email email,
            Genero genero,
            Cpf cpf,
            Perfil perfil,
            LocalDate dataNascimento,
            String telefone) {
        String senha = cpf.toString().substring(0,3) + perfil.name().toLowerCase();
        var funcionario = Usuario.createFuncionario(
                Nome.of(nome),
                email,
                Senha.ofHash(Objects.requireNonNull(passwordEncoder.encode(senha))),
                genero,
                cpf,
                perfil,
                DataNascimento.of(dataNascimento),
                Telefone.of(telefone)
        );
        return repository.salvar(funcionario);

    }

    @Override
    public UsuarioResumo buscarPorId(UUID id) {
        var usuario = repository.buscarPorId(id);
       return UsuarioResumo.ofUsuario(usuario);

    }

    @Override
    public Usuario buscarUsuarioPorId(UUID id) {
        return repository.buscarPorId(id);
    }

    @Override
    public UsuarioResumo buscarPorEmail(Email email) {
        var usuario = repository.buscarPorEmail(email);
        return UsuarioResumo.ofUsuario(usuario);
    }

    @Override
    public UsuarioResumo trocarTelefone(UUID usuarioId,String telefone) {
        var usuario = repository.buscarPorId(Objects.requireNonNull(usuarioId));
        usuario.mudarTelefone(Telefone.of(telefone));
        var usuarioAtualizado =  repository.atualizar(usuario);
        return UsuarioResumo.ofUsuario(usuarioAtualizado);
    }

    @Override
    public UsuarioResumo trocarDataNascimento(UUID usuarioId,LocalDate dataNascimento) {
        var usuario = repository.buscarPorId(Objects.requireNonNull(usuarioId));
        usuario.mudarDataNascimento(DataNascimento.of(dataNascimento));
        var usuarioAtualizado = repository.atualizar(usuario);
        return UsuarioResumo.ofUsuario(usuarioAtualizado);

    }

    @Override
    public boolean existePorCpf(Cpf cpf) {
        return repository.existePorCpf(cpf);
    }


}
