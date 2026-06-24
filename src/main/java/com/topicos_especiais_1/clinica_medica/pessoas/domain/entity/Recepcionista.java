package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "recepcionistas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recepcionista extends BaseEntity implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    private Recepcionista(Usuario usuario) {
        this.usuario = Objects.requireNonNull(usuario);
    }
    public static Recepcionista create(Usuario usuario) {
        return new Recepcionista(usuario);
    }

}
