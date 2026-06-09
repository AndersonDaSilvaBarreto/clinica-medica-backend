package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.PacienteId;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "pacientes")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private PacienteId id;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private UUID usuarioId;

    @Embedded
    private CPF cpf;

    @Embedded
    private DataNascimento dataNascimento;


    
}
