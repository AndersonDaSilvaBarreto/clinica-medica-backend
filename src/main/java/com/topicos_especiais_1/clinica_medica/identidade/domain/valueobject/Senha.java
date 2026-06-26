package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.service.SenhaValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public final class Senha implements Serializable {

    @Column(name = "senha", nullable = false)
    private String value;

    protected Senha() {
    }

    private Senha(@NonNull String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static Senha of(@NonNull String value) {
        SenhaValidator.validar(value);
        return new Senha(value);
    }

    public static Senha ofHash(@NonNull String hash) {
        return new Senha(hash);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senha senha = (Senha) o;
        return Objects.equals(value, senha.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
