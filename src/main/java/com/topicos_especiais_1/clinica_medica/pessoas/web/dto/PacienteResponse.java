package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;


import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;

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
    public static PacienteResponse ofPacienteAndUsuario(Paciente paciente, UsuarioResumo usuarioResumo) {
            return new PacienteResponse(
                    paciente.getId(),
                    usuarioResumo.nome(),
                    usuarioResumo.email().toString(),
                    usuarioResumo.telefone().orElse(null)
                    ,usuarioResumo.cpf(),
                    usuarioResumo.dataNascimento(),
                    paciente.getEndereco()
            );
    }

}
