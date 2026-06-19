package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;


import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(
        UUID id,
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dataNascimento,
        String endereco
) {
    public static PacienteResponse ofPacienteAndUsuarioAutenticado(Paciente paciente, UsuarioAutenticado usuarioAutenticado) {
            return new PacienteResponse(
                    paciente.getId(),
                    usuarioAutenticado.getNome(),
                    usuarioAutenticado.getEmail().toString(),
                    usuarioAutenticado.getTelefone().orElse(null),
                    usuarioAutenticado.getCpf().toString(),
                    usuarioAutenticado.getDataNascimento().orElse(null),
                    paciente.getEndereco()
            );
    }
    public static PacienteResponse ofPacienteAndUsuario(Paciente paciente, Usuario usuario) {
        return new PacienteResponse(
                paciente.getId(),
                usuario.getNome().toString(),
                usuario.getEmail().toString(),
                usuario.getTelefone().map(Telefone::toString).orElse(null),
                usuario.getCpf().toString(),
                usuario.getDataNascimento().map(DataNascimento::getValue).orElse(null),
                paciente.getEndereco()
        );


}}
