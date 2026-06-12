package com.topicos_especiais_1.clinica_medica.identidade.api.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record UsuarioResumo(
        UUID id,
        String nome,
        Email email,
        Perfil perfil,
        Optional<String> telefone,
        boolean ativo,
        Genero genero,
        String cpf,
        LocalDate dataNascimento

) {
    public static UsuarioResumo ofUsuario(Usuario usuario) {
        return new UsuarioResumo(
              usuario.getId(),
                usuario.getNome().toString(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getTelefone().map(Telefone::toString),
                usuario.getAtivo(),
                usuario.getGenero(),
                usuario.getCpf().toString(),
                usuario.getDataNascimento().map(DataNascimento::getValue).orElse(null)
        );
    }
}
