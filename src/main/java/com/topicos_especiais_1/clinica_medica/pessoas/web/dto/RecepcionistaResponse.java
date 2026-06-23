package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record RecepcionistaResponse(
        UUID id,
    String nome,
    String cpf,
    String email,
    String telefone,
    LocalDate dataNascimento,
        Instant dataCriacao
) {
    public static RecepcionistaResponse of(Recepcionista recepcionista) {
        return new RecepcionistaResponse(
                recepcionista.getId(),
                recepcionista.getUsuario().getNome().toString(),
                recepcionista.getUsuario().getCpf().toString(),
                recepcionista.getUsuario().getEmail().toString(),
                recepcionista.getUsuario().getTelefone().map(Telefone::toString).orElse(null),
                recepcionista.getUsuario().getDataNascimento().map(DataNascimento::getValue).orElse(null),
                recepcionista.getDataCriacao()

        );
    }
}
