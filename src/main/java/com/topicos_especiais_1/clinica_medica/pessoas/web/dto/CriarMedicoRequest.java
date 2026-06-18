package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

public record CriarMedicoRequest(
        @NotBlank(message = "Nome não pode ser nulo")
        @Length(min = 2, max = 150, message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @Length(min = 11, max = 11, message = "cpf deve ter exatamente 11 caracteres")
        String cpf,
        @NotBlank
        @Email
        String email,
        String telefone,
        String crm,
        Integer tempoConsultaMinutos,
        List<UUID> especialidade,
        Genero genero
) {
}
