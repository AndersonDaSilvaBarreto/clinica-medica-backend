package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
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
    private DataNascimento dataNascimento;

    @Column(name = "endereco", length = 500)
    private String endereco;

        private Paciente(
            @NonNull UUID usuarioId,
            DataNascimento dataNascimento,
            String endereco
            ) {
        this.usuarioId = usuarioId;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }
    public static Paciente create(
            @NonNull UUID usuarioId,
            DataNascimento dataNascimento,
            String endereco) {
        return new Paciente(usuarioId,dataNascimento,endereco);
    }

}
