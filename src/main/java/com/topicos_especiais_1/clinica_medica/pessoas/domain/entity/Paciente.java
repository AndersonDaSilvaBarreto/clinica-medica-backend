package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
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


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;


    @Column(name = "endereco", length = 500)
    private String endereco;

        private Paciente(
            @NonNull Usuario usuario,
            String endereco
            ) {
        this.usuario = usuario;
        this.endereco = endereco;
    }
    public void mudarEndereco(String endereco) {
            this.endereco = Objects.requireNonNull(endereco);
    }
    public static Paciente create(
            Usuario usuario,
            String endereco) {
        return new Paciente(Objects.requireNonNull(usuario),endereco);
    }


}
