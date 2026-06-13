package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;


import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Telefone;
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
    public static PacienteResponse ofPacienteAndUsuario(Paciente paciente, UsuarioAutenticado usuarioAutenticado) {
            return new PacienteResponse(
                    paciente.getId(),
                    usuarioAutenticado.getNome().toString(),
                    usuarioAutenticado.getEmail().toString(),
                    usuarioAutenticado.getTelefone().map(Telefone::toString).orElse(null),
                    usuarioAutenticado.getCpf().toString(),
                    usuarioAutenticado.getDataNascimento().map(DataNascimento::getValue).orElse(null),
                    paciente.getEndereco()
            );
    }

}
