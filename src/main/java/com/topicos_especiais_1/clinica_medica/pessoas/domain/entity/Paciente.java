package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paciente extends BaseEntity implements Serializable {


    @Column(name = "usuario_id", nullable = false, unique = true)
    private UUID usuarioId;


    @Column(name = "endereco", length = 500)
    private String endereco;

        private Paciente(
            @NonNull UUID usuarioId,
            String endereco
            ) {
        this.usuarioId = usuarioId;
        this.endereco = endereco;
    }
    public void mudarEndereco(String endereco) {
            this.endereco = Objects.requireNonNull(endereco);
    }
    public static Paciente create(
            UUID usuarioId,
            String endereco) {
        return new Paciente(Objects.requireNonNull(usuarioId),endereco);
    }


}
