package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;
import jakarta.persistence.*;
import lombok.AccessLevel;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Table(name = "pacientes")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paciente extends BaseEntity {


    @Column(name = "usuario_id", nullable = false, unique = true)
    private UUID usuarioId;

    @Embedded
    private CPF cpf;

    @Embedded
    private DataNascimento dataNascimento;

    @Column(name = "endereco", length = 500)
    private String endereco;

    @Column(name = "convenio_id")
    private UUID convenioId;

    private Paciente(
            @NonNull UUID usuarioId,
            @NonNull CPF cpf,
            DataNascimento dataNascimento,
            String endereco,
            UUID convenioId) {
        this.usuarioId = usuarioId;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.convenioId = convenioId;
    }
    public static Paciente create(
            @NonNull UUID usuarioId,
            @NonNull CPF cpf,
            DataNascimento dataNascimento,
            String endereco,
            UUID convenioId) {
        return new Paciente(usuarioId,cpf,dataNascimento,endereco,convenioId);
    }
    
}
