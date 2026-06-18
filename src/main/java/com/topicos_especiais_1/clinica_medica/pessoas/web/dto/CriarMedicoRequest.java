package com.topicos_especiais_1.clinica_medica.pessoas.web.dto;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Genero;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CriarMedicoRequest(
        @NotBlank(message = "Nome não pode ser nulo")
        @Length(min = 2, max = 150, message = "Nome deve ter de 2 até 150 caracteres")
        String nome,
        @NotBlank(message = "Cpf não pode ser vazio")
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,
        String telefone,
        @NotBlank(message = "Crm não pode ser Vazio")
        String crm,
        @Positive(message = "Tempo de consulta é em minutos e não pode ser nagativo ou zero")
        Integer tempoConsultaMinutos,
        @NotEmpty(message = "Deve haver pelo menos uma especialidade")
        List<UUID> especialidades,
        @NotNull(message = "Genero não pode ser vazio")
        Genero genero,
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento


) {
}
