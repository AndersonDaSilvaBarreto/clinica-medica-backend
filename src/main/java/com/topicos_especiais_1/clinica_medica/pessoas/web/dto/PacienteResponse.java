package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;


import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;

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
                    usuarioAutenticado.getNome().toString(),
                    usuarioAutenticado.getEmail().toString(),
                    usuarioAutenticado.getTelefone().orElse(null),
                    usuarioAutenticado.getCpf().toString(),
                    usuarioAutenticado.getDataNascimento().orElse(null),
                    paciente.getEndereco()
            );
    }
    public static PacienteResponse ofPacienteAndUsuarioResumo(
            Paciente paciente,
            UsuarioResumo usuarioResumo) {
        return new PacienteResponse(
                paciente.getId(),
                usuarioResumo.nome(),
                usuarioResumo.email().toString(),
                usuarioResumo.telefone().orElse(null),
                usuarioResumo.cpf(),
                usuarioResumo.dataNascimento(),
                paciente.getEndereco()
        );

    }

}
